package alexander.skornyakov.yourwords.data.entity

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.google.firebase.firestore.Exclude

data class Word (

    @get:Exclude var id: String = "",

    var word: String = "",

    var wordSetId: String = "",

    @get:Exclude var meanings: ObservableList<Meaning> = ObservableArrayList<Meaning>()

)