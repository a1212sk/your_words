package alexander.skornyakov.yourwords.di.main

import alexander.skornyakov.yourwords.ui.main.cards.WordFragment
import alexander.skornyakov.yourwords.ui.main.newword.NewWordFragment
import alexander.skornyakov.yourwords.ui.main.sets.SetsFragment
import alexander.skornyakov.yourwords.ui.main.wordslist.WordsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSetsFragment():SetsFragment

    @ContributesAndroidInjector
    abstract fun contributeWordListFragment():WordsListFragment

    @ContributesAndroidInjector
    abstract fun contributeCardsFragment():WordFragment

    @ContributesAndroidInjector
    abstract fun contributeNewWordFragment():NewWordFragment

}