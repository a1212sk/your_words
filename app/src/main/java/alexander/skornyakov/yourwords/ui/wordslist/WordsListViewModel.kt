package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.data.entity.Word
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WordsListViewModel(
    app: Application,
    selectedWordsSet: Long
): AndroidViewModel(app){

    private val _navigateToCards = MutableLiveData<Boolean>()
    val navigateToCards : LiveData<Boolean>
        get() = _navigateToCards

    fun goToCards(){
        _navigateToCards.value = true
    }

    fun goToCardsCompleted(){
        _navigateToCards.value = false
    }

    private val _words = MutableLiveData<List<Word>>()//db.getWordsBySetId(selectedWordsSet)
    val words : LiveData<List<Word>>
        get() = _words
}