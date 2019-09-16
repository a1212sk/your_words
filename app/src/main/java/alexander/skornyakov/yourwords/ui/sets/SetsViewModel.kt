package alexander.skornyakov.yourwords.ui.sets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetsViewModel : ViewModel() {

    private val _navigateToWordsList = MutableLiveData<Boolean?>()
    val navigateToWordsList : LiveData<Boolean?>
        get() = _navigateToWordsList

    fun goToWordsList(){
        _navigateToWordsList.value = true;
    }

    fun goToWordsListCompleted(){
        _navigateToWordsList.value = false;
    }

}
