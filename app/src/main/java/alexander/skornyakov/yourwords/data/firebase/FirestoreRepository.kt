package alexander.skornyakov.yourwords.data.firebase

import alexander.skornyakov.yourwords.data.entity.WordsSet
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(){

    private val firestore = FirebaseFirestore.getInstance()
   // private val user = FirebaseAuth.getInstance().currentUser

    fun saveWordSet(ws: WordsSet): Task<Void>{
        var ref = firestore.collection("sets").document()
        return ref.set(ws)
    }

    fun getWordSets(): CollectionReference{
        return firestore.collection("sets")
    }

    fun removeWordSet(ws: WordsSet): Task<Void> {
        var ref = firestore.collection("sets").document(ws.id.toString())
        return ref.delete()
    }

}