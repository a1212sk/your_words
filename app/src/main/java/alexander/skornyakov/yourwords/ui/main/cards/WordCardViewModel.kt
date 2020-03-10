package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class WordCardViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var repository: FirestoreRepository

    private lateinit var _wordId : String
    fun setWordId(id : String){
        _wordId = id
    }

    private var _word = MutableLiveData<Word>()
    val word : LiveData<Word>
        get() {
            if(_word.value==null){
                repository.getWordById(_wordId).addOnSuccessListener {
                    _word.value = it
                }
            }
            return _word
        }
}
