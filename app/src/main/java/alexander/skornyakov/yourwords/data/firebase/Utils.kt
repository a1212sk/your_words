package alexander.skornyakov.yourwords.data.firebase

import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.data.entity.WordsSet
import android.app.Activity
import android.util.Log
import androidx.databinding.ObservableArrayList
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class Utils {
    companion object{
        fun populateDb(firestoreRepository: FirestoreRepository, fileName: String, userId: String, context: Activity){
            Log.d("Population","Start")
            val jsonString = getData(context.assets.open(fileName))
            val json = JSONObject(jsonString)
            val setsArray = json.getJSONArray("sets")
            for (i in 0 until setsArray.length()){
                val set = setsArray.getJSONObject(i)
                val setName = set.getString("name")
                val wordSet = WordsSet()
                wordSet.name = setName
                wordSet.public = false
                wordSet.userID = userId
                firestoreRepository.saveWordSet(wordSet).continueWith {
                        val words = set.getJSONArray("words")
                        for (j in 0 until words.length()) {

                            val word = words.getJSONObject(j)
                            val wordName = word.getString("word")
                            val meanings = word.getJSONArray("meanings")
                            val meaningsList = ObservableArrayList<Meaning>()
                            for (k in 0 until meanings.length()) {

                                val meaning = meanings.getJSONObject(k)
                                val meaningString = meaning.getString("meaning")
                                val m = Meaning()
                                m.order = k
                                m.meaning = meaningString
                                meaningsList.add(m)

                            }

                            val newWord = Word()
                            newWord.word = wordName
                            newWord.wordSetId = it.result!!
                            newWord.meanings = meaningsList

                            firestoreRepository.saveWord(newWord).addOnSuccessListener {
                                Log.d("population","WORD SAVED: "+newWord.word)
                            }

                    }

                }.addOnSuccessListener { Log.d("Population",setName + " set's loaded") }
            }
            Log.d("Population","End")
        }

        private fun getData(stream: InputStream): String? {
            var tContents: String? = ""
            try {
                val size: Int = stream.available()
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                tContents = String(buffer)
            } catch (e: IOException) {
                Log.e("Population: getData",e.message)
            }
            return tContents
        }
    }
}
