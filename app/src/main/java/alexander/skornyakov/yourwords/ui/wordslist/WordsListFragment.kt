package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.WordslistFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class WordsListFragment : Fragment() {

    companion object {
        fun newInstance() = WordsListFragment()
    }

    private lateinit var viewModel: WordsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : WordslistFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.wordslist_fragment,
            container,
            false)
        viewModel = ViewModelProviders.of(this).get(WordsListViewModel::class.java)
        binding.wordsListViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }



}