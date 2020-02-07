package alexander.skornyakov.yourwords.di

import alexander.skornyakov.yourwords.di.auth.AuthFragmentBuildersModule
import alexander.skornyakov.yourwords.di.auth.AuthModule
import alexander.skornyakov.yourwords.di.auth.AuthViewModelsModule
import alexander.skornyakov.yourwords.di.main.MainFragmentBuildersModule
import alexander.skornyakov.yourwords.di.main.MainModule
import alexander.skornyakov.yourwords.di.main.MainViewModelsModule
import alexander.skornyakov.yourwords.ui.auth.AuthActivity
import alexander.skornyakov.yourwords.ui.main.MainActivity
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [MainActivityModule::class,
            MainViewModelsModule::class,
            MainModule::class,
            MainFragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [AuthFragmentBuildersModule::class,
            AuthModule::class,
            AuthViewModelsModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

}