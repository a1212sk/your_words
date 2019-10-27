package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.data.room.Meaning
import alexander.skornyakov.yourwords.data.room.Word
import alexander.skornyakov.yourwords.data.room.WordsDao
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class WordViewModel(
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

    private val _meanings = db.getMeaningsByWordId(selectedWordId)
    val meanings : LiveData<List<Meaning>>
        get() = _meanings

}