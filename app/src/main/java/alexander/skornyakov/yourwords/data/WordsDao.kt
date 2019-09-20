package alexander.skornyakov.yourwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsDao {
    @Insert
    fun insert(w: Word)

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
}