package alexander.skornyakov.yourwords.di

import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory):ViewModelProvider.Factory

}