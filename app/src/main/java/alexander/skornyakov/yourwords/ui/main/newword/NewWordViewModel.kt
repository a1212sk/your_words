package alexander.skornyakov.yourwords.ui.main.newword

import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import java.lang.RuntimeException
import javax.inject.Inject

class NewWordViewModel @Inject constructor(): ViewModel(){

    @Inject lateinit var repository: FirestoreRepository

    val word = MutableLiveData<Word>()
    init{
        word.value = Word()
    }
    lateinit var setID : String

    private val _meaningsToBeRemoved = MutableLiveData<MutableList<Meaning>>()
    val meaningsToBeRemoved : LiveData<MutableList<Meaning>>
        get()=_meaningsToBeRemoved

    fun addMeaningToBeRemoved(meaning: Meaning){
        if(_meaningsToBeRemoved.value==null){
            _meaningsToBeRemoved.value = mutableListOf()
        }
        val list = _meaningsToBeRemoved.value?.toMutableList()
        list?.add(meaning)
        _meaningsToBeRemoved.postValue(list)
    }

    fun removeMeaningToBeRemoved(meaning: Meaning){
        val list = _meaningsToBeRemoved.value?.toMutableList()
        list?.remove(meaning)
        _meaningsToBeRemoved.postValue(list)
    }

    fun addMeaning(str: String){
        val meaning = Meaning(meaning = str)
        word.value?.meanings?.add(meaning)
        word.value = word.value
    }

    fun removeMeaning(m: Meaning){
        word.value?.meanings?.remove(m)
        word.value = word.value
    }

    fun saveWord(): Task<Void>{
        word.value?.wordSetId = setID
        return repository.saveWord(word.value!!)
    }

    fun setWord(wordId: String) {
        repository.getWordById(wordId).addOnSuccessListener {
            it?.let {
                word.value = it
            }
        }
    }

}