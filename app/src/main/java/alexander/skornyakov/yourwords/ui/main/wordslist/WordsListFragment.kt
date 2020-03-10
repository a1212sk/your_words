package alexander.skornyakov.yourwords.ui.main.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.WordslistFragmentBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import java.lang.RuntimeException
import javax.inject.Inject

class WordsListFragment : DaggerFragment(){

    @Inject lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var viewModel: WordsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflation
        val binding : WordslistFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.wordslist_fragment,
            container,
            false)
        binding.lifecycleOwner = viewLifecycleOwner

        // Get view model and pass it to binding
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(WordsListViewModel::class.java)
        binding.wordsListViewModel = viewModel

        // Has option menu
        setHasOptionsMenu(true)

        // Get word id
        val selectedWordsSet = arguments?.getString("setId") ?: throw RuntimeException("Set ID id wasn't passed!")
        viewModel.setId = selectedWordsSet

        // Create words click listener
        val wordsListClickListener = WordsClickListener { view, wordId ->
            when(view.id) {
                R.id.constraintLayout -> {
                    findNavController().navigate(
                        WordsListFragmentDirections.actionWordsListFragmentToCardsFragment(
                            wordId
                        )
                    )
                }
                R.id.word_delete_btn -> {
                    viewModel.removeWord(wordId)
                }
                R.id.word_edit_btn -> {
                    findNavController().navigate(
                        WordsListFragmentDirections.actionWordsListFragmentToNewWordFragment(viewModel.setId!!,wordId)
                    )
                }
            }
        }

        // Create and init adapter
        val wordsListRecyclerViewAdapter =
            WordsListRecyclerViewAdapter(wordsListClickListener)
        binding.setsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = wordsListRecyclerViewAdapter
        }

        // Observe words list and pass it to the adapter
        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let {
                wordsListRecyclerViewAdapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.wordlist_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        goToCreateNewWordFragment()
        return true
    }

    private fun goToCreateNewWordFragment() {
        findNavController().navigate(
            WordsListFragmentDirections.actionWordsListFragmentToNewWordFragment(viewModel.setId!!,null)
        )
    }

}

