package alexander.skornyakov.yourwords.di.main

import alexander.skornyakov.yourwords.di.ViewModelKey
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.ui.main.sets.SetsViewModel
import alexander.skornyakov.yourwords.ui.main.wordslist.WordsListViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SetsViewModel::class)
    abstract fun bindSetsViewModel(viewModel: SetsViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordsListViewModel::class)
    abstract fun bindWordsListViewModel(viewModel: WordsListViewModel):ViewModel

}