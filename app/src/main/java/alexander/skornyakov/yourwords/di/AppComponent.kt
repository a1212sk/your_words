package alexander.skornyakov.yourwords.di

import alexander.skornyakov.yourwords.app.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class])
interface AppComponent : AndroidInjector<BaseApplication>{
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun app(app:BaseApplication):Builder
        fun build(): AppComponent
    }
}