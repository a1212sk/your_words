package alexander.skornyakov.yourwords.ui.main.sets

import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.data.entity.WordsSet
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SetsViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var sessionManager: SessionManager

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error
    fun clearError(){
        _error.value=null
    }

    @Inject lateinit var repository : FirestoreRepository
    //val currentUser = sessionManager.getUser().value?.data

    //Creates new set of words
    fun createSet(text: String) {
        val ws = WordsSet()
        ws.name = text.toString()
        sessionManager.getUser().value?.data.let { ws.userID = it?.uid!! }
        AsyncTask.execute {
            repository.saveWordSet(ws).addOnFailureListener {
                _error.value = it.toString()
            }
        }
    }

    fun deleteSet(ws: WordsSet){
        AsyncTask.execute{
            repository.removeWordSet(ws).addOnFailureListener {
                _error.value = it.toString()
            }
        }
    }

    private val _wordsSetList: MutableLiveData<List<WordsSet>> = MutableLiveData()
    val wordsSetList: LiveData<List<WordsSet>>
        get() {
            repository.getWordSets().addSnapshotListener { snapshot, e ->
                val list = mutableListOf<WordsSet>()
                for (set in snapshot!!) {
                    val ws = set.toObject(WordsSet::class.java)
                    ws.id = set.id
                    list.add(ws)
                }
                list.sortBy {
                    it.name
                }
                _wordsSetList.value = list
            }
            return _wordsSetList
        }

}