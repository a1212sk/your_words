package alexander.skornyakov.yourwords.ui.main.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.WordslistFragmentBinding
import alexander.skornyakov.yourwords.ui.main.wordslist.WordsListFragmentDirections
import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class WordsListFragment : Fragment(){

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

        val selectedWordsSet = arguments?.getLong("id")


        val app: Application = requireNotNull(this.activity).application
        val vmFactory =
            WordsListViewModelFactory(
                app,
                selectedWordsSet!!
            )
        viewModel = ViewModelProviders.of(this, vmFactory).get(WordsListViewModel::class.java)
        binding.wordsListViewModel = viewModel

        val wordsListRecyclerViewAdapter =
            WordsListRecyclerViewAdapter(
                WordsClickListener { wordId ->
                    wordId.let {
                        findNavController().navigate(
                            WordsListFragmentDirections.actionWordsListFragmentToCardsFragment(
                                it,
                                selectedWordsSet
                            )
                        )

                    }
                })
        binding.setsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = wordsListRecyclerViewAdapter

        }

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let {
                wordsListRecyclerViewAdapter.submitList(it)
            }
        })


        return binding.root
    }


}

