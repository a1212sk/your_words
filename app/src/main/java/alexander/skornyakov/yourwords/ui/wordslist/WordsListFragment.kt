package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.WordslistFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

class WordsListFragment : Fragment() {

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
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(WordsListViewModel::class.java)
        binding.wordsListViewModel = viewModel

        viewModel.navigateToCards.observe(this, Observer {
            if(it == true){
                findNavController().navigate(R.id.action_wordsList_Fragment_to_cardsFragment)
                viewModel.goToCardsCompleted()
            }
        })


        return binding.root
    }



}