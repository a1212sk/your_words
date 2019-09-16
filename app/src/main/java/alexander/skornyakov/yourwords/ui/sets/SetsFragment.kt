package alexander.skornyakov.yourwords.ui.sets

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.SetsFragmentBinding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

class SetsFragment : Fragment() {

    companion object {
        fun newInstance() = SetsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate xml
        val binding = DataBindingUtil.inflate<SetsFragmentBinding>(
            inflater, R.layout.sets_fragment,container,false)
        binding.lifecycleOwner = this
        //Set data.ViewModel var from xml
        val vm: SetsViewModel = ViewModelProviders.of(this).get(SetsViewModel::class.java)
        binding.setsViewModel = vm
        //Observing navigation variable inside ViewModel
        vm.navigateToWordsList.observe(this, Observer {
            if(it == true){
                findNavController().navigate(R.id.action_setsFragment_to_wordsList_Fragment)
                vm.goToWordsListCompleted()
            }
        })

        return binding.root
    }


}
