package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.databinding.WordFragmentBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.word_fragment.*
import java.lang.RuntimeException
import javax.inject.Inject

class WordsFragment : DaggerFragment(){

    @Inject lateinit var factory: ViewModelProviderFactory
    lateinit var viewModel: WordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val selectedWord = arguments?.getString("wordId")
        selectedWord?:throw RuntimeException("Selected word is null!")

        val binding: WordFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.word_fragment,
            container,
            false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this,factory).get(WordsViewModel::class.java)
        viewModel.setWordId(selectedWord)
        this.selectedWordId = selectedWord

        viewModel.words.observe(this, Observer {
            it.let{
                //Toast.makeText(context,it.joinToString("\n\n"),Toast.LENGTH_LONG).show()
                pages = it.count()
                binding.pager.adapter = ScreenSlidePagerAdapter(this,it)
            }
        })

        return binding.root
    }

    var pages = 0
    var selectedWordId = ""

   inner class ScreenSlidePagerAdapter(f: Fragment, val words: List<Word>):FragmentStateAdapter(f){
        override fun getItemCount(): Int {
            return words.size
        }

        override fun createFragment(position: Int): Fragment {
            return WordCardFragment.create(words[position].id)
        }

    }

}




