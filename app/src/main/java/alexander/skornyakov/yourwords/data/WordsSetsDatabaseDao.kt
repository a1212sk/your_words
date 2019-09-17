package alexander.skornyakov.yourwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsSetsDatabaseDao {
    @Insert
    fun insert(night: WordsSet)

    @Update
    fun update(night: WordsSet)

    @Query("SELECT * from words_sets_table WHERE id = :key")
    fun get(key: Long): WordsSet?

    @Query("DELETE FROM words_sets_table")
    fun clear()

    @Query("SELECT * FROM words_sets_table ORDER BY id DESC")
    fun getAll(): LiveData<List<WordsSet>>

}
