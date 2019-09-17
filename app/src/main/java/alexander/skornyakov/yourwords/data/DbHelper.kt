package alexander.skornyakov.yourwords.data

class DbHelper {
    companion object{
        fun prepopulateWordsSets(dao: WordsSetsDatabaseDao){
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
    }
}