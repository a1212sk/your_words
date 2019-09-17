package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.data.WordsSet
import alexander.skornyakov.yourwords.data.WordsSetsDao
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SetsViewModel (val db: WordsSetsDao,
                     app: Application): AndroidViewModel(app) {

    private val _navigateToWordsList = MutableLiveData<Boolean?>()
    val navigateToWordsList : LiveData<Boolean?>
        get() = _navigateToWordsList

    private val _wordsSetList = db.getAll()
    val wordsSetList : LiveData<List<WordsSet>>
        get() = _wordsSetList

    fun goToWordsList(){
        _navigateToWordsList.value = true;
    }

    fun goToWordsListCompleted(){
        _navigateToWordsList.value = false;
    }



}
