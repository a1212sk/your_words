package alexander.skornyakov.yourwords.data.firebase

import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.entity.WordsSet
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(){

    @Inject lateinit var firestore : FirebaseFirestore
   // private val user = FirebaseAuth.getInstance().currentUser

    fun saveWordSet(ws: WordsSet): Task<Void>{
        var ref = firestore.collection("sets").document()
        return ref.set(ws)
    }

    fun getWordSetsByUserID(userID: String): Query{
        return firestore.collection("sets").whereEqualTo("userID",userID)
    }

    fun removeWordSet(ws: WordsSet): Task<Void> {
        var ref = firestore.collection("sets").document(ws.id.toString())
        return ref.delete()
    }

    fun renameWordSet(ws: WordsSet,newName: String) : Task<Void>{
        val ref = firestore.collection("sets").document(ws.id)
        return ref.set(hashMapOf("name" to newName), SetOptions.merge())
    }

    fun getWordListBySetID(setId: String) : Query{
        return firestore.collection("words").whereEqualTo("wordSetId",setId)
    }

    fun saveWord(w: Word):Task<Void>{
        var ref = firestore.collection("words").document()
        return ref.set(w)
    }

    fun renameWord(w: Word, newName: String):Task<Void>{
        val ref = firestore.collection("words").document(w.id)
        return ref.set(hashMapOf("name" to newName), SetOptions.merge())
    }

}