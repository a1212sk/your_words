package alexander.skornyakov.yourwords.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.Executors


@Database(entities = [WordsSet::class, Word::class, Meaning::class], version = 2, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {

    abstract val wordsSetsDao: WordsSetsDao
    abstract val wordsDao: WordsDao

    companion object {

        @Volatile
        private var INSTANCE: WordsDatabase? = null

        private fun loadJSONFromAsset(context: Context): String? {
            //function to load the JSON from the Asset and return the object
            var json: String? = null
            val charset: Charset = Charsets.UTF_8
            try {
                val `is` = context.assets.open("db.json")
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, charset)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }

        fun getInstance(context: Context): WordsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                val rdc = object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        Executors.newSingleThreadScheduledExecutor()
                            .execute {

                                val jsonString = loadJSONFromAsset(context)
                                val json = JSONObject(jsonString)

                                val sets = json.getJSONArray("sets")
                                for(i in 0 until sets.length()){
                                    val set = sets.getJSONObject(i)
                                    val setName = set.getString("name")
                                    val wordsSet = WordsSet(0, setName)
                                    val wsId = instance?.wordsSetsDao?.insert(wordsSet)
                                    val words = set.getJSONArray("words")
                                    for(j in 0 until words.length()){
                                        val word = words.getJSONObject(j)
                                        val wordName = word.getString("word")
                                        val newWord = Word(0, wordName, wsId)
                                        val wId = instance?.wordsDao?.insertWord(newWord)
                                        val meanings = word.getJSONArray("meanings")
                                        for(k in 0 until meanings.length()){
                                            val meaning = meanings.getJSONObject(k)
                                            val meaningString = meaning.getString("meaning")
                                            val newMeaning = Meaning(0, wId!!, meaningString, k)
                                            instance?.wordsDao?.insertMeaning(newMeaning)
                                        }
                                    }
                                }

                            }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        // do something every time database is open
                    }
                }

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordsDatabase::class.java,
                        "words_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(rdc)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}