package alexander.skornyakov.yourwords.ui.main.newword

import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class NewWordViewModel @Inject constructor(): ViewModel(){

    @Inject lateinit var repository: FirestoreRepository

    val word = MutableLiveData<String>()
    lateinit var setID : String

    private val _meanings = MutableLiveData<MutableList<Meaning>>()
    val meanings : LiveData<MutableList<Meaning>>
        get()=_meanings

    fun addMeaning(str: String){
        val meaning = Meaning(meaning = str)
        if(_meanings.value==null)_meanings.value = mutableListOf()
        val list = _meanings.value?.toMutableList()
        list?.add(meaning)
        _meanings.postValue(list)
    }

    fun saveWord(): Task<Void>{
        val newWord = Word()
        newWord.word = word.value.toString()
        newWord.wordSetId = setID
        val listOfMeanings = _meanings.value?.toList()
        return repository.saveWord(newWord, listOfMeanings)
    }

}