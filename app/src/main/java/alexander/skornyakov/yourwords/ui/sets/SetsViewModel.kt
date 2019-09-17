package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.data.WordsSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetsViewModel : ViewModel() {

    private val _navigateToWordsList = MutableLiveData<Boolean?>()
    val navigateToWordsList : LiveData<Boolean?>
        get() = _navigateToWordsList

    private val _wordsSetList = MutableLiveData<List<WordsSet>>()
    var wordsSetList : MutableLiveData<List<WordsSet>>
        get() = _wordsSetList
        set(value) { _wordsSetList.value = value.value}

    fun goToWordsList(){
        _navigateToWordsList.value = true;
    }

    fun goToWordsListCompleted(){
        _navigateToWordsList.value = false;
    }

}
