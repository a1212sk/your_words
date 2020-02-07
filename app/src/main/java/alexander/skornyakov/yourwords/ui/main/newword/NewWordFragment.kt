package alexander.skornyakov.yourwords.ui.main.newword

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import alexander.skornyakov.yourwords.databinding.NewWordFragmentBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewWordFragment : DaggerFragment(){

    @Inject lateinit var repository: FirestoreRepository
    private lateinit var vm: NewWordViewModel
    @Inject lateinit var viewModelFactory: ViewModelProviderFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vm = ViewModelProviders.of(this,viewModelFactory).get(NewWordViewModel::class.java)

        val binding = DataBindingUtil.inflate<NewWordFragmentBinding>(
            inflater,
            R.layout.new_word_fragment,
            container,
            false)

        binding.saveButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}