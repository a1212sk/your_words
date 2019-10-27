package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.data.room.WordsSet
import alexander.skornyakov.yourwords.data.room.WordsSetsDao
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SetsViewModel (
    db: WordsSetsDao,
    app: Application): AndroidViewModel(app) {

    private val db = db

    //Creates new set of words
    fun createSet(text: String) {
        val ws = WordsSet()
        ws.name = text.toString()
        AsyncTask.execute {
            db.insert(ws)
        }
    }

//    private val _navigateToWordsList = MutableLiveData<Boolean?>()
//    val navigateToWordsList : LiveData<Boolean?>
//        get() = _navigateToWordsList

    private val _wordsSetList = db.getAll()
    val wordsSetList : LiveData<List<WordsSet>>
        get() = _wordsSetList

//    fun goToWordsList(){
//        _navigateToWordsList.value = true;
//    }
//
//    fun goToWordsListCompleted(){
//        _navigateToWordsList.value = false;
//    }



}
