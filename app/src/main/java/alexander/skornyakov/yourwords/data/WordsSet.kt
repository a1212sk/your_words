package alexander.skornyakov.yourwords.data

class WordsSet constructor(val _id: Int?,val _word: String) {
    val id: Int? = _id
    val word: String? = _word
    val meaning: String? = ""

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}