package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.entity.Word
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WordViewModel(
    app: Application,
    selectedWordsSetId: Long,
    selectedWordId: Long
): AndroidViewModel(app){

    private val _word = MutableLiveData<Word>()//db.get(selectedWordId)
    val word : LiveData<Word>?
        get() = _word

    private val _words = MutableLiveData<List<Word>>()//db.getWordsBySetId(selectedWordsSetId)
    val words : LiveData<List<Word>>
        get() = _words

    private val _meanings = MutableLiveData<List<Meaning>>()//db.getMeaningsByWordId(selectedWordId)
    val meanings : LiveData<List<Meaning>>
        get() = _meanings

}