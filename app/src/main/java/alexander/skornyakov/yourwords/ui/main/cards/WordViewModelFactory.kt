package alexander.skornyakov.yourwords.ui.main.cards

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WordViewModelFactory(
    private val app: Application,
    private val selectedWordsSetId: Long,
    private val selectedWordId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(
                app,
                selectedWordsSetId,
                selectedWordId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}