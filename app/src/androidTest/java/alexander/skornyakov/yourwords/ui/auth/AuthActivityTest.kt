package alexander.skornyakov.yourwords.ui.auth

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.di.DaggerAppComponent
import alexander.skornyakov.yourwords.ui.main.MainActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import javax.inject.Inject


class AuthActivityTest{

//    @Before
//    fun setUp() {
//        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
//        val appComponent = DaggerAppComponent.builder().app(app as BaseApplication).build()
//        appComponent.inject(app)
//        println("OOOOOO ")
//    }



    @Test
    fun testSignIn() {

        val scenario = ActivityScenario.launch(AuthActivity::class.java)
        try {
            onView(withId(R.id.drawer)).perform(DrawerActions.open())
            onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.signout_menu_item))
        }catch (e:Exception){}

        onView(withId(R.id.email_text_edit)).perform(typeText("h@h.ru"))
        onView(withId(R.id.password_text_edit)).perform(typeText("linux1"))
        onView(withId(R.id.sign_in_button)).perform(click())
        Thread.sleep(2000)


    }
}