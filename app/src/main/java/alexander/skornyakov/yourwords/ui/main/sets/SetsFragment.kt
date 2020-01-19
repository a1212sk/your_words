package alexander.skornyakov.yourwords.ui.main.sets

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.SetsFragmentBinding
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager


private const val SPAN_COUNT = 2

class SetsFragment : Fragment() {

    private lateinit var vm: SetsViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sets_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.add_set_dialog,null);
        val editText = view.findViewById<EditText>(R.id.new_set_name)
        builder.setView(view)
            .setTitle("Add new set")
            .setPositiveButton("Create",
                DialogInterface.OnClickListener{dialog, id->
                    if(!editText.text.isNullOrEmpty()){
                        vm.createSet(editText.text.toString())
                    }
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener{dialog, id->

                })

        builder.show()
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        //Inflate xml
        val binding = DataBindingUtil.inflate<SetsFragmentBinding>(
            inflater, R.layout.sets_fragment,container,false)
        binding.lifecycleOwner = this

        val app: Application = requireNotNull(this.activity).application

        //Set data.ViewModel var from xml
        val setsViewModelFactory =
            SetsViewModelFactory(app)
        val vm: SetsViewModel = ViewModelProviders.of(this, setsViewModelFactory).get(
            SetsViewModel::class.java)
        binding.setsViewModel = vm
        this.vm = vm

        val setsRecyclerViewAdapter =
            SetsRecyclerViewAdapter(
                SetsClickListener { set ->
                    set.let {
                        findNavController().navigate(
                            SetsFragmentDirections.actionSetsFragmentToWordsListFragment(
                                it.id
                            )
                        )
                    }
                },
                SetsClickListener { set ->
                    Toast.makeText(context, set.toString(), Toast.LENGTH_LONG).show()
                },
                SetsClickListener { set ->
                    Toast.makeText(context, "RENAME " + set.toString(), Toast.LENGTH_LONG).show()
                },
                SetsClickListener { set ->
                    vm.deleteSet(set)
                    Toast.makeText(context, "DELETE " + set.toString(), Toast.LENGTH_LONG).show()
                }
            )

        binding.setsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,
                SPAN_COUNT
            )
            adapter = setsRecyclerViewAdapter
        }

        vm.wordsSetList.observe(viewLifecycleOwner, Observer {

            it?.let {
                setsRecyclerViewAdapter.submitList(it)
            }
        })

        vm.error.observe(viewLifecycleOwner, Observer {
            it?.let{
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                vm.clearError()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

}