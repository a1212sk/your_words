package alexander.skornyakov.yourwords.data.entity

data class Word (

    var wordId: Long = 0L,

    val word: String,

    val wordSetId: Long?

)