package alexander.skornyakov.yourwords.di

import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    AppModule::class])
interface AppComponent : AndroidInjector<BaseApplication>{

    fun sessionManager():SessionManager

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun app(app:BaseApplication):Builder
        fun build(): AppComponent
    }
}