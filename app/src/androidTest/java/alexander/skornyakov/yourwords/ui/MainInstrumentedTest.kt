package alexander.skornyakov.yourwords.ui

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.WordsSet
import alexander.skornyakov.yourwords.ui.main.MainActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.sets_fragment.view.*
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun fillRecycleView(){
        onView(withHint("Email...")).perform(typeText("h@h.ru"))
        onView(withHint("Password...")).perform(typeText("linux1"))
        onView(withText("SIGN IN")).perform(click())
        Thread.sleep(2000)
        val rv = activityRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
        check(rv!=null)
        check(rv.adapter!=null)
        Thread.sleep(2000)
        val count = rv.adapter!!.itemCount
        check(count>0)
    }
}