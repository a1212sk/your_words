package alexander.skornyakov.yourwords.ui

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.ui.main.MainActivity
import alexander.skornyakov.yourwords.ui.main.sets.SetsRecyclerViewAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.hamcrest.CoreMatchers.allOf

import org.junit.Before
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


//    val loginTimeConst = 2000L
//    val waitAfterCreateSet = 500L
//
//
//    fun signIn(){
//        if(activityRule.activity.findViewById<Toolbar>(R.id.toolbar).title=="Your words") {
//            onView(withId(R.id.drawer)).perform(DrawerActions.open())
//            onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.signout_menu_item))
//        }
//        onView(withHint("Email...")).perform(typeText("h@h.ru"))
//        onView(withHint("Password...")).perform(typeText("linux1"))
//        onView(withText("SIGN IN")).perform(click())
//
//        Thread.sleep(loginTimeConst)
//    }
//
//    fun getRecyclerViewItemsAmount(id:Int):Int{
//        val rv = activityRule.activity.findViewById<RecyclerView>(id)
//        check(rv!=null)
//        check(rv.adapter!=null)
//        val count = rv.adapter!!.itemCount
//        return count
//    }
//
//    @Test
//    fun fillRecycleView(){
//        signIn()
//        val rv = activityRule.activity.findViewById<RecyclerView>(alexander.skornyakov.yourwords.R.id.setsRecyclerView)
//        check(rv!=null)
//        check(rv.adapter!=null)
//        Thread.sleep(loginTimeConst)
//        val count = rv.adapter!!.itemCount
//        check(count>0)
//    }
//
//    @Test
//    fun createAndRemoveSets(){
//        signIn()
//        for(i in 0..5) {
//            onView(withText("ADD SET")).perform(click())
//            val text = Random.nextInt().toString()
//            onView(withHint("Set name ...")).perform(typeText(text)) //type random number
//            onView(withText("CREATE")).perform(click())
//            Thread.sleep(waitAfterCreateSet)
//            val beforeAmount = getRecyclerViewItemsAmount(R.id.setsRecyclerView)
//            onView(withId(R.id.setsRecyclerView))
//                .perform(
//                    RecyclerViewActions
//                        .actionOnItem<SetsRecyclerViewAdapter.WordViewHolder>(
//                            hasDescendant(withText(text)), longClick()
//                        )
//                )
//            onView(allOf(withText("Delete"), withEffectiveVisibility(Visibility.VISIBLE))).perform(
//                click()
//            )
//            val afterAmount = getRecyclerViewItemsAmount(R.id.setsRecyclerView)
//            check(beforeAmount - afterAmount == 1)
//        }
//
//    }
}