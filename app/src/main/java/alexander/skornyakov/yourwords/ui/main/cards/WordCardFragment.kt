package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.databinding.FragmentWordCardBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_word_card.*
import javax.inject.Inject


class WordCardFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelProviderFactory

    private lateinit var currentWordId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
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

        val viewModel = ViewModelProviders.of(this,factory).get(WordCardViewModel::class.java)
        viewModel.setWordId(currentWordId)

        viewModel.word.observe(this, Observer {
            it.let {
                binding.word = it
                binding.cardMeanings.layoutManager = LinearLayoutManager(context)
                val adapter = WordCardMeaningsRVAdapter()
                adapter.submitList(it.meanings)
                binding.cardMeanings.adapter = adapter

            }
        })

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
