package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.FragmentWordCardBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class WordCardFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelProviderFactory
    @Inject lateinit var tts: TextToSpeech

    private lateinit var currentWordId : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentWordCardBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_word_card,
            container,
            false)
        binding.lifecycleOwner = this

        binding.cardMeanings.layoutManager = LinearLayoutManager(context)
        val adapter = WordCardMeaningsRVAdapter()
        binding.cardMeanings.adapter = adapter

        val viewModel = ViewModelProviders.of(this,factory).get(WordCardViewModel::class.java)
        if(::currentWordId.isInitialized) {
            viewModel.setWordId(currentWordId)
        }

        viewModel.word.observe(this, Observer {
            it.let {
                binding.word = it
                adapter.submitList(it.meanings)
            }
        })

        binding.sndBtn.setOnClickListener {
            if (!::tts.isInitialized)println("tts is not initialized!")
            else tts.speak(viewModel.word.value?.word, TextToSpeech.QUEUE_FLUSH, null)
        }

        return binding.root
    }

    companion object{
        fun create(wordId: String):WordCardFragment{
            val fragment = WordCardFragment()
            fragment.currentWordId = wordId
            return fragment
        }
    }

}
