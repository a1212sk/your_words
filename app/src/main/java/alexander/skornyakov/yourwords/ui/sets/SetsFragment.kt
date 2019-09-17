package alexander.skornyakov.yourwords.ui.sets

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.WordsDatabase
import alexander.skornyakov.yourwords.data.WordsSet
import alexander.skornyakov.yourwords.data.WordsSetsDatabaseDao
import alexander.skornyakov.yourwords.databinding.SetsFragmentBinding
import android.app.Application
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val SPAN_COUNT = 2

class SetsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate xml
        val binding = DataBindingUtil.inflate<SetsFragmentBinding>(
            inflater, R.layout.sets_fragment,container,false)
        binding.lifecycleOwner = this

        val app: Application = requireNotNull(this.activity).application
        val wordsSetsDao: WordsSetsDatabaseDao = WordsDatabase.getInstance(app).wordsSetsDatabaseDao

        //Set data.ViewModel var from xml
        val setsViewModelFactory = SetsViewModelFactory(wordsSetsDao,app)
        val vm: SetsViewModel = ViewModelProviders.of(this, setsViewModelFactory).get(SetsViewModel::class.java)
        binding.setsViewModel = vm
        //Observing navigation variable inside ViewModel
        vm.navigateToWordsList.observe(this, Observer {
            if(it == true){
                findNavController().navigate(R.id.action_setsFragment_to_wordsList_Fragment)
                vm.goToWordsListCompleted()
            }
        })

        val setsRecyclerViewAdapter = SetsRecyclerViewAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = setsRecyclerViewAdapter
        }

        vm.wordsSetList.observe(viewLifecycleOwner, Observer {
            it?.let {
                setsRecyclerViewAdapter.submitList(it)
            }
        })




        return binding.root
    }


}
