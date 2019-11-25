package alexander.skornyakov.yourwords.data.entity

import com.google.firebase.firestore.Exclude

data class WordsSet(

    @get:Exclude var id: String = "",

    var name: String = "",

    var public: Boolean = false,

    var userID: String = ""

)