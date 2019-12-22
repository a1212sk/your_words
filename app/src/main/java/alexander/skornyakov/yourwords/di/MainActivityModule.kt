package alexander.skornyakov.yourwords.di

import alexander.skornyakov.yourwords.ui.main.MainActivity
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun getFirebaseAnalitycs(mainActivity: MainActivity): FirebaseAnalytics {
            return FirebaseAnalytics.getInstance(mainActivity);
        }
    }
}