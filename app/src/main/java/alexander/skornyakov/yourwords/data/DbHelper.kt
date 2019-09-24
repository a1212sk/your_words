package alexander.skornyakov.yourwords.data

import java.util.*

class DbHelper {
    companion object{
        fun prepopulateWordsSets(dao: WordsSetsDao){
            val sets = listOf<String>(
                "Greetings",
                "Animals",
                "Computer",
                "Verbs",
                "Phrasal Verbs",
                "Bathroom",
                "Body"
            )
            for(s in sets){
                dao.insert(WordsSet(0,s))
            }


        }

        fun prepopulateWords(dao: WordsDao){
            val animals= mapOf<String,String>(
                "a cat" to "a small animal with fur, four legs, and a tail that is kept as a pet",
                "a dog" to "an animal with fur, four legs, and a tail that is kept as a pet, or trained to guard buildings and guide blind people",
                "a mouse" to "a small animal with fur and a long, thin tail"
            )
            for (a in animals){
                val w = Word(0,a.key,2)
                dao.insertWord(w)

                val m = Meaning(0,w.wordId, a.value,0)
                dao.insertMeaning(m)
            }
        }
    }
}