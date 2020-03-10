package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.databinding.WordFragmentBinding
import alexander.skornyakov.yourwords.databinding.WordMeaningItemBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import java.lang.RuntimeException
import javax.inject.Inject

class WordFragment : DaggerFragment(){

    @Inject lateinit var factory: ViewModelProviderFactory
    lateinit var viewModel: WordViewModel

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

        viewModel = ViewModelProviders.of(this,factory).get(WordViewModel::class.java)

        return binding.root
    }



}