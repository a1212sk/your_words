package alexander.skornyakov.yourwords.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_sets_table")
data class WordsSet (
    @PrimaryKey(autoGenerate = true)
    var setId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = ""

)