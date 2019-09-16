package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.CardsFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class CardsFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: CardsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.cards_fragment,
            container,
            false);
        val vm = ViewModelProviders.of(this).get(CardsViewModel::class.java)
        binding.cardsViewModel = vm
        return binding.root
    }

}