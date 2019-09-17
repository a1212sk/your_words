package alexander.skornyakov.yourwords

import alexander.skornyakov.yourwords.data.WordsDatabase
import alexander.skornyakov.yourwords.data.WordsSet
import alexander.skornyakov.yourwords.data.WordsSetsDao
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import java.io.IOException
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DdTest {

    private lateinit var db: WordsDatabase
    private lateinit var setsDao: WordsSetsDao



    @Before
    fun createDb(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext,WordsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        setsDao = db.wordsSetsDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        if(db!=null)
            db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertWordsSet(){
        val ws = WordsSet()
        ws.name = "Verbs"
        setsDao.insert(ws)

    }

}
