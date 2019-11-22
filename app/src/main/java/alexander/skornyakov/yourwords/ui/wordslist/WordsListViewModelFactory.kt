package alexander.skornyakov.yourwords.ui.wordslist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WordsListViewModelFactory(
    private val app: Application,
    private val selectedWordsSet: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsListViewModel::class.java)) {
            return WordsListViewModel(app, selectedWordsSet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}