package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class WordViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var repository: FirestoreRepository


}