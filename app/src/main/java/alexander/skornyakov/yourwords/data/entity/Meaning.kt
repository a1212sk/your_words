package alexander.skornyakov.yourwords.data.entity

import com.google.firebase.firestore.Exclude

data class Meaning (

    @get:Exclude var id: String = "",

    var wordId: String = "",

    var meaning: String,

    var order: Int = 0
)