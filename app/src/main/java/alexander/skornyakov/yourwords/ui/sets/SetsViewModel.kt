package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.data.entity.WordsSet
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SetsViewModel (app: Application): AndroidViewModel(app) {

    val repository = FirestoreRepository()

    //Creates new set of words
    fun createSet(text: String) {
        val ws = WordsSet()
        ws.name = text.toString()
        AsyncTask.execute {
            repository.saveWordSet(ws)
        }
    }

    private val _wordsSetList : MutableLiveData<List<WordsSet>> = MutableLiveData()

    val wordsSetList : LiveData<List<WordsSet>>
        get() {
            val list = mutableListOf<WordsSet>()
            repository.getWordSets().addSnapshotListener { snapshot, e ->
                for (set in snapshot!!){
                    list.add(set.toObject(WordsSet::class.java))
                }
                _wordsSetList.value = list
            }

            return _wordsSetList
        }

}
