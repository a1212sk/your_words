package alexander.skornyakov.yourwords.ui.main.newword

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.data.firebase.FirestoreRepository
import alexander.skornyakov.yourwords.databinding.NewWordFragmentBinding
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
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
        vm = ViewModelProviders.of(this,viewModelFactory).get(NewWordViewModel::class.java)

        /// Arguments
        val selectedWordSet = arguments?.getString("setId")
        selectedWordSet?:throw RuntimeException("SetID is not selected!")
        vm.setID = selectedWordSet
        val word = arguments?.getString("wordId")
        word?.let{
            vm.setWord(word)
        }

        val binding = DataBindingUtil.inflate<NewWordFragmentBinding>(
            inflater,
            R.layout.new_word_fragment,
            container,
            false)
        binding.vm = vm

        binding.lifecycleOwner = viewLifecycleOwner

        val meaningClickListener = MeaningClickListener{view, m ->
            when(view.id){
                R.id.checkBox->{
                    if((view as CheckBox).isChecked){
                        vm.addMeaningToBeRemoved(m)
                    }else{
                        vm.removeMeaningToBeRemoved(m)
                    }
                }
            }
        }
        val adapter = MeaningsAdapter(meaningClickListener)
        binding.recyclerView.apply {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        binding.saveButton.setOnClickListener {
            val word = vm.word.value
            if(word?.meanings == null || word.meanings.count()==0){
                Toast.makeText(context,"Empty word or the meaning of the word!",Toast.LENGTH_LONG).show()
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

        binding.removeMeaningButton.setOnClickListener {
            vm.meaningsToBeRemoved.value?:return@setOnClickListener
            for(mId in vm.meaningsToBeRemoved.value!!){
                vm.removeMeaning(mId)
            }
            vm.meaningsToBeRemoved.value?.clear()
        }

        vm.word.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.meanings)
                adapter.notifyDataSetChanged()
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