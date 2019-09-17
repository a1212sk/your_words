package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.data.Word
import alexander.skornyakov.yourwords.data.WordsDao
import alexander.skornyakov.yourwords.data.WordsSet
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WordsListViewModel(
    val db: WordsDao,
    app: Application
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

    private val _words = db.getAll() //TODO key from previous fragment for getWordsBySetId
    val words : LiveData<List<Word>>
        get() = _words
}