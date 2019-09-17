package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.data.WordsSetsDatabaseDao
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SetsViewModelFactory(
    private val wordsSetsDao: WordsSetsDatabaseDao,
    private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetsViewModel::class.java)) {
            return SetsViewModel(wordsSetsDao, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
