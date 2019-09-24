package alexander.skornyakov.yourwords.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "meanings_table", foreignKeys = [ForeignKey(
    entity = Word::class,
    parentColumns = arrayOf("wordId"),
    childColumns = arrayOf("word_id")
)]
)
data class Meaning (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "word_id")
    @NotNull
    val wordId: Long = 0,

    @ColumnInfo(name = "meaning")
    @NotNull
    val meaning: String,

    @ColumnInfo(name = "order")
    @NotNull
    val order: Int = 0
)