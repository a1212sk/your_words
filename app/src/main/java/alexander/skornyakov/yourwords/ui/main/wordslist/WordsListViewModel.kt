package alexander.skornyakov.yourwords.ui.main.wordslist

import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import java.lang.RuntimeException
import javax.inject.Inject

class WordsListViewModel @Inject constructor(): ViewModel(){

    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var repository : FirestoreRepository

    private val _navigateToCards = MutableLiveData<Boolean>()
    val navigateToCards : LiveData<Boolean>
        get() = _navigateToCards

    fun goToCards(){
        _navigateToCards.value = true
    }

    fun goToCardsCompleted(){
        _navigateToCards.value = false
    }

    private var listenerRegistration: ListenerRegistration? = null
    private val _words = MutableLiveData<List<Word>>()
    val words : LiveData<List<Word>>
        get() {
            if(listenerRegistration==null){
                initWordsListListenerRegistration()
            }
            return _words
        }

    private fun initWordsListListenerRegistration(){
        setId ?: throw RuntimeException("Set ID is null!")
        listenerRegistration = repository.getWordListBySetID(setId!!)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot ?: throw RuntimeException(firebaseFirestoreException?.message)
                val wordList = mutableListOf<Word>()
                for (w in querySnapshot){
                    val word = w.toObject(Word::class.java)
                    word.id = w.id
                    wordList.add(word)
                }
                wordList.sortBy { it.word }
                _words.value = wordList
            }
    }

    // must be initialized in fragment
    var setId : String? = null

    override fun onCleared() {
        listenerRegistration?.remove()
        super.onCleared()
    }
}