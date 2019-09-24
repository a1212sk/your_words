package alexander.skornyakov.yourwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsDao {
    @Insert
    fun insertWord(w: Word) : Long

    @Insert
    fun insertMeaning(m: Meaning) : Long

    @Update
    fun update(w: Word)

    @Query("SELECT * from words_table WHERE wordId = :key")
    fun get(key: Long): LiveData<Word>?

    @Query("DELETE FROM words_table")
    fun clear()

    @Query("SELECT * FROM words_table ORDER BY wordId ASC")
    fun getAll(): LiveData<List<Word>>

    @Query("SELECT * FROM words_table WHERE wordset_id = :key ORDER BY wordId ASC")
    fun getWordsBySetId(key: Long): LiveData<List<Word>>

    @Query("SELECT * FROM meanings_table WHERE word_id = :key ORDER BY `order` ASC")
    fun getMeaningsByWordId(key: Long): LiveData<List<Meaning>>

}