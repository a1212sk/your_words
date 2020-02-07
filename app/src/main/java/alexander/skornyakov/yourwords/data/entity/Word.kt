package alexander.skornyakov.yourwords.data.entity

import com.google.firebase.firestore.Exclude

data class Word (

    @get:Exclude var id: String = "",

    var word: String = "",

    var wordSetId: String = ""

)