package alexander.skornyakov.yourwords.ui.main.sets

import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.data.entity.WordsSet
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import javax.inject.Inject

class SetsViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var repository : FirestoreRepository

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error
    fun clearError(){
        _error.value=null
    }


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

    fun renameSet(ws: WordsSet,newName: String){
        CoroutineScope(IO).launch {
            val oldName = ws.name
            repository.renameWordSet(ws,newName)
        }
    }

    private fun initWordSetListener(){
        //unsubscribe if not null
        wordsSetListenerRegistration?.remove()

        val userID = sessionManager.getUser().value?.data?.uid
            ?: throw RuntimeException("Cannot get the user!")
        wordsSetListenerRegistration = repository.getWordSetsByUserID(userID)
            .addSnapshotListener { snapshot, e ->
                snapshot?.let {
                    Log.d("SetsViewModel", "SnapshotListener worked out!!!")
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
            }
    }
    private var wordsSetListenerRegistration : ListenerRegistration? = null
    private val _wordsSetList: MutableLiveData<List<WordsSet>> = MutableLiveData()
    val wordsSetList: LiveData<List<WordsSet>>
        get() {
            if(wordsSetListenerRegistration==null) {
                initWordSetListener()
            }
            return _wordsSetList
        }

}