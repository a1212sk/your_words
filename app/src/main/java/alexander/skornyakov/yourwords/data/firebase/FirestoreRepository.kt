package alexander.skornyakov.yourwords.data.firebase

import alexander.skornyakov.yourwords.data.entity.WordsSet
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    fun saveWordSet(ws: WordsSet){
        var ref = firestore.collection("sets").document()
        ref.set(ws)
    }

    fun getWordSets(): CollectionReference{
        return firestore.collection("sets")
    }

    fun removeWordSet(ws: WordsSet): Task<Void> {
        var ref = firestore.collection("sets").document(ws.id.toString())
        return ref.delete()
    }

}