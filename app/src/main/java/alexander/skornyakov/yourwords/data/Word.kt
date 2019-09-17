package alexander.skornyakov.yourwords.data

import androidx.room.*
import org.jetbrains.annotations.NotNull

@Entity(tableName = "words_table",indices = [Index("wordset_id")],foreignKeys = arrayOf(ForeignKey(
    entity = WordsSet::class,
    parentColumns = arrayOf("setId"),
    childColumns = arrayOf("wordset_id")
)))
data class Word (

    @PrimaryKey(autoGenerate = true)
    var wordId: Long = 0L,

    @ColumnInfo(name="word")
    @NotNull
    val word: String,

    @ColumnInfo(name="meaning")
    @NotNull
    val meaning: String,

    @ColumnInfo(name="wordset_id")
    @NotNull
    val wordSetId: Long?

)