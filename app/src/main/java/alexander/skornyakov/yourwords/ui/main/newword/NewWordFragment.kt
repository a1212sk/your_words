package alexander.skornyakov.yourwords.ui.main.newword

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import alexander.skornyakov.yourwords.databinding.NewWordFragmentBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.support.DaggerFragment
import java.lang.RuntimeException
import javax.inject.Inject

class NewWordFragment : DaggerFragment(){

    private lateinit var vm: NewWordViewModel
    @Inject lateinit var viewModelFactory: ViewModelProviderFactory


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val selectedWordSet = arguments?.getString("setId")
        selectedWordSet?:throw RuntimeException("SetID is not selected!")

        vm = ViewModelProviders.of(this,viewModelFactory).get(NewWordViewModel::class.java)
        vm.setID = selectedWordSet

        val binding = DataBindingUtil.inflate<NewWordFragmentBinding>(
            inflater,
            R.layout.new_word_fragment,
            container,
            false)
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = MeaningsAdapter()
        binding.recyclerView.apply {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        binding.saveButton.setOnClickListener {
            if(vm.word.value.isNullOrEmpty()){
                Toast.makeText(context,"Empty word!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            vm.saveWord().addOnSuccessListener {
                findNavController().navigateUp()
            }
            .addOnFailureListener {
                Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
            }
        }

        binding.addMeaningButton.setOnClickListener {
            showAddMeaningDialog()
        }

        vm.meanings.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it.joinToString(), Toast.LENGTH_LONG).show()
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    private fun showAddMeaningDialog() {
        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.add_set_dialog, null);
        val editText = view.findViewById<EditText>(R.id.new_set_name)
        builder.setView(view)
            .setTitle("Add a new meaning")
            .setPositiveButton("Add") { _, id ->
                if (!editText.text.isNullOrEmpty()) {
                    vm.addMeaning(editText.text.toString())
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
            }
        builder.show()
    }
}