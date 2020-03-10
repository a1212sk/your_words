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
import javax.inject.Singleton

class WordsViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var repository: FirestoreRepository

    // you have to set word id right after init ViewModel
    private lateinit var _wordId : String
    fun setWordId(id: String){
        _wordId = id
    }

    private var _words : MutableLiveData<List<Word>> = MutableLiveData()
    val words : LiveData<List<Word>>
        get() {
            if(_words.value==null){
                repository.getWordById(_wordId).addOnSuccessListener { word ->
                    repository.getWordListBySetID(word.wordSetId).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        _words.value = mutableListOf<Word>()
                        if (querySnapshot != null) {
                            for (word in querySnapshot){
                                repository.getWordById(word.id).addOnSuccessListener {
                                    (_words.value as MutableList<Word>).add(it)
                                    _words.value = _words.value
                                }
                            }
                        }
                    }
                }
            }
            return _words
        }
}