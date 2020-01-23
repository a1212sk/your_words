package alexander.skornyakov.yourwords.di.main

import alexander.skornyakov.yourwords.di.ViewModelKey
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel):ViewModel

}