package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.data.Word
import alexander.skornyakov.yourwords.data.WordsDao
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CardsViewModel(
    val db: WordsDao,
    app: Application,
    selectedWordsSetId: Long,
    selectedWordId: Long
): AndroidViewModel(app){

    private val _word = db.get(selectedWordId)
    val word : LiveData<Word>?
        get() = _word

    private val _words = db.getWordsBySetId(selectedWordsSetId)
    val words : LiveData<List<Word>>
        get() = _words

}