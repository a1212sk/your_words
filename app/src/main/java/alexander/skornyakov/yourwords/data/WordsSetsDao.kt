package alexander.skornyakov.yourwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsSetsDao {
    @Insert
    fun insert(ws: WordsSet)

    @Update
    fun update(ws: WordsSet)

    @Query("SELECT * from words_sets_table WHERE setId = :key")
    fun get(key: Long): WordsSet?

    @Query("DELETE FROM words_sets_table")
    fun clear()

    @Query("SELECT * FROM words_sets_table ORDER BY setId DESC")
    fun getAll(): LiveData<List<WordsSet>>

}
