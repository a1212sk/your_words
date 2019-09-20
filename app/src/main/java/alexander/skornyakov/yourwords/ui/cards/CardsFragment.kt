package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.WordsDao
import alexander.skornyakov.yourwords.data.WordsDatabase
import alexander.skornyakov.yourwords.databinding.CardsFragmentBinding
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper

class CardsFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: CardsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.cards_fragment,
            container,
            false)
        binding.lifecycleOwner = this

        val app: Application = requireNotNull(this.activity).application
        val wordsDao: WordsDao = WordsDatabase.getInstance(app).wordsDao
        val selectedWordsSet = arguments?.getLong("setId")
        val selectedWord = arguments?.getLong("wordId")

        val vmFactory = CardsViewModelFactory(wordsDao, app, selectedWordsSet!!, selectedWord!!)

        val vm = ViewModelProviders.of(this, vmFactory).get(CardsViewModel::class.java)
        binding.cardsViewModel = vm

        return binding.root
    }

}