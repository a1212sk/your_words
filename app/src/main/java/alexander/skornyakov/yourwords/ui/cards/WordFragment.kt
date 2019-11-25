package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.databinding.WordFragmentBinding
import alexander.skornyakov.yourwords.databinding.WordMeaningItemBinding
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class WordFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: WordFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.word_fragment,
            container,
            false)
        binding.lifecycleOwner = this

        val app: Application = requireNotNull(this.activity).application
        val selectedWordsSet = arguments?.getLong("id")
        val selectedWord = arguments?.getLong("wordId")

        val vmFactory = WordViewModelFactory(app, selectedWordsSet!!, selectedWord!!)

        val vm = ViewModelProviders.of(this, vmFactory).get(WordViewModel::class.java)
        binding.wordViewModel = vm

        vm.meanings.observe(this,Observer{
            for(m in it){
                val meaningItemBinding = createMeaningsListItem(m, inflater, container)
                binding.meanings.addView(meaningItemBinding.root)
            }
        })

        return binding.root
    }

    fun createMeaningsListItem(meaning: Meaning, inflater: LayoutInflater, container: ViewGroup?): WordMeaningItemBinding{
        val binding = DataBindingUtil.inflate<WordMeaningItemBinding>(
            inflater,
            R.layout.word_meaning_item,
            container,
            false
        )
        binding.meaning = meaning
        return binding
    }

}