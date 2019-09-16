package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class WordsListFragment : Fragment() {

    companion object {
        fun newInstance() = WordsListFragment()
    }

    private lateinit var viewModel: WordsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.wordslist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WordsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}