package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.data.WordsDao
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CardsViewModelFactory(
    private val wordsDao: WordsDao,
    private val app: Application,
    private val selectedWordsSet: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardsViewModel::class.java)) {
            return CardsViewModel(wordsDao, app, selectedWordsSet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}