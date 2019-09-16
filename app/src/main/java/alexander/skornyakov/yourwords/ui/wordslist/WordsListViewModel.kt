package alexander.skornyakov.yourwords.ui.wordslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordsListViewModel : ViewModel() {

    private val _navigateToCards = MutableLiveData<Boolean>()
    val navigateToCards : LiveData<Boolean>
        get() = _navigateToCards

    fun goToCards(){
        _navigateToCards.value = true
    }

    fun goToCardsCompleted(){
        _navigateToCards.value = false
    }

}