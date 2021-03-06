package alexander.skornyakov.yourwords.data.firebase

import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.entity.WordsSet
import androidx.databinding.ObservableArrayList
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(){

    @Inject lateinit var firestore : FirebaseFirestore
   // private val user = FirebaseAuth.getInstance().currentUser

    fun saveWordSet(ws: WordsSet): Task<String>{
        var ref = firestore.collection("sets").document()
        val id = ref.id
        return ref.set(ws).continueWith {
            return@continueWith id
        }
    }

    fun getWordSetsByUserID(userID: String): Query{
        return firestore.collection("sets").whereEqualTo("userID",userID)
    }

    fun removeWordSet(ws: WordsSet){
        var sets = firestore.collection("sets").document(ws.id.toString())
        var words = firestore.collection("words").whereEqualTo("wordSetId",ws.id)
        var task = words.get().addOnSuccessListener {
            for (w in it){
                w.reference.collection("meanings").get()
                   .addOnSuccessListener {
                        for(m in it){
                            m.reference.delete()
                        }
                        w.reference.delete()
                    }
            }
            sets.delete()
        }
    }

    fun renameWordSet(ws: WordsSet,newName: String) : Task<Void>{
        val ref = firestore.collection("sets").document(ws.id)
        return ref.set(hashMapOf("name" to newName), SetOptions.merge())
    }

    fun getWordListBySetID(setId: String) : Query{
        return firestore.collection("words").whereEqualTo("wordSetId",setId)
    }

    fun getWordById(wId: String): Task<Word>{
        val refWord = firestore.collection("words").document(wId)
        var word : Word? = null
        return refWord.get() //Get the word
            // fill word object
            .continueWith {
                val ds = it.result
                word = ds?.toObject(Word::class.java)
                word?.id = wId
                word?.meanings = ObservableArrayList()
                word
            }
            .continueWithTask {
                refWord.collection("meanings").get().continueWith {qs->
                    for(m in qs.result!!){
                        val meaning = m.toObject(Meaning::class.java)
                        meaning.id = m.id
                        word?.meanings?.add(meaning)
                    }
                    word!!
                }
            }
    }

    fun saveWord(w: Word):Task<Void>{
        var ref : DocumentReference? = null
        if(w.id.isNullOrEmpty()){
            ref = firestore.collection("words").document()
        }
        else ref = firestore.collection("words").document(w.id)

        return ref.set(w).addOnSuccessListener {
            ref.collection("meanings").get().addOnSuccessListener {
                for(m in it){
                    m.reference.delete()
                }
                for(m in w.meanings){
                    ref.collection("meanings").add(m)
                }
            }
        }
    }

    fun renameWord(w: Word, newName: String):Task<Void>{
        val ref = firestore.collection("words").document(w.id)
        return ref.set(hashMapOf("name" to newName), SetOptions.merge())
    }

    fun removeWord(wId: String):Task<Void>{
        val ref = firestore.collection("words").document(wId)
        ref.collection("meanings").get()
            .addOnSuccessListener {
                for(m in it){
                    m.reference.delete()
                }
            }
        return ref.delete()
    }

}