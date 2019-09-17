package alexander.skornyakov.yourwords.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors


@Database(entities = [WordsSet::class, Word::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {

    abstract val wordsSetsDao: WordsSetsDao
    abstract val wordsDao: WordsDao

    companion object {

        @Volatile
        private var INSTANCE: WordsDatabase? = null

        fun getInstance(context: Context): WordsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                val rdc = object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        Executors.newSingleThreadScheduledExecutor()
                            .execute(Runnable {
                                instance?.wordsSetsDao?.let {
                                    DbHelper.prepopulateWordsSets(it)
                                }
                                instance?.wordsDao?.let {
                                    DbHelper.prepopulateWords(it)
                                }
                            })
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