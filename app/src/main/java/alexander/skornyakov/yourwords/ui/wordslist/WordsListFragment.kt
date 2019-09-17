package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.WordsDao
import alexander.skornyakov.yourwords.data.WordsDatabase
import alexander.skornyakov.yourwords.data.WordsSetsDao
import alexander.skornyakov.yourwords.databinding.WordslistFragmentBinding
import alexander.skornyakov.yourwords.ui.sets.SetsRecyclerViewAdapter
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

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

        val app: Application = requireNotNull(this.activity).application
        val wordsDao: WordsDao = WordsDatabase.getInstance(app).wordsDao
        val vmFactory = WordsListViewModelFactory(wordsDao,app)
        viewModel = ViewModelProviders.of(this, vmFactory).get(WordsListViewModel::class.java)
        binding.wordsListViewModel = viewModel

        viewModel.navigateToCards.observe(this, Observer {
            if(it == true){
                findNavController().navigate(R.id.action_wordsList_Fragment_to_cardsFragment)
                viewModel.goToCardsCompleted()
            }
        })

        val wordsListRecyclerViewAdapter = WordsListRecyclerViewAdapter()
        binding.recyclerView.apply {
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