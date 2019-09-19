package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.data.Word
import alexander.skornyakov.yourwords.data.WordsDao
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CardsViewModel(
    val db: WordsDao,
    app: Application,
    selectedWordsSet: Long
): AndroidViewModel(app){

    private val _words = db.getWordsBySetId(selectedWordsSet)
    val words : LiveData<List<Word>>
        get() = _words

}