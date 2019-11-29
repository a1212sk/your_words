package alexander.skornyakov.yourwords.ui

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.ui.main.MainActivity
import alexander.skornyakov.yourwords.ui.sets.SetsRecyclerViewAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import org.junit.Before
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    fun signIn(){
        if(activityRule.activity.findViewById<Toolbar>(R.id.toolbar).title=="Your words") {
            onView(withId(R.id.drawer)).perform(DrawerActions.open())
            onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.signout_menu_item))
        }
        onView(withHint("Email...")).perform(typeText("h@h.ru"))
        onView(withHint("Password...")).perform(typeText("linux1"))
        onView(withText("SIGN IN")).perform(click())
        Thread.sleep(2000)
    }

    @Test
    fun fillRecycleView(){
        signIn()
        val rv = activityRule.activity.findViewById<RecyclerView>(alexander.skornyakov.yourwords.R.id.setsRecyclerView)
        check(rv!=null)
        check(rv.adapter!=null)
        Thread.sleep(2000)
        val count = rv.adapter!!.itemCount
        check(count>0)
    }

    @Test
    fun addNewSets(){
        signIn()
        onView(withText("ADD SET")).perform(click())
        val text = Random.nextInt().toString()
        onView(withHint("Set name ...")).perform(typeText(text)) //type random number
        onView(withText("CREATE")).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.setsRecyclerView))
            .perform(RecyclerViewActions
                .actionOnItem<SetsRecyclerViewAdapter.WordViewHolder>(
                    hasDescendant(withText(text)), click()))
    }
}