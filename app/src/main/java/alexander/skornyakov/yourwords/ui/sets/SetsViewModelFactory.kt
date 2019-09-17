package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.data.WordsSetsDao
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SetsViewModelFactory(
    private val wordsSetsDao: WordsSetsDao,
    private val app: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetsViewModel::class.java)) {
            return SetsViewModel(wordsSetsDao, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
