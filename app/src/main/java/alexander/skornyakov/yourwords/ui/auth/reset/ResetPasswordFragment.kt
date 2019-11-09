package alexander.skornyakov.yourwords.ui.auth.reset

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.ResetPasswordFragmentBinding
import alexander.skornyakov.yourwords.ui.auth.signin.SignInViewModel
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.ui.main.MainViewModelFactory
import alexander.skornyakov.yourwords.util.Utils
import android.app.Application
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordFragment : Fragment() {

    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var mAuth: FirebaseAuth
    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val app: Application = requireNotNull(this.activity).application
        mAuth = FirebaseAuth.getInstance()

        val resetPasswordViewModelFactory = ResetPasswordViewModelFactory(app, mAuth)
        viewModel = ViewModelProviders.of(this,resetPasswordViewModelFactory).get(ResetPasswordViewModel::class.java)

        val binding = DataBindingUtil.inflate<ResetPasswordFragmentBinding>(
            inflater,
            R.layout.reset_password_fragment,
            container,
            false)
        binding.viewModel = viewModel

        viewModel.emailSent.observe(this, Observer {
            if(it){
                Toast.makeText(context,"Check your mail!",Toast.LENGTH_LONG).show()
                viewModel.resetEmailSent()
                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToSignInFragment())
                Utils.hideKeyboard(activity!!)
            }
        })

        viewModel.error.observe(this, Observer {
            if(!it.isNullOrEmpty())
            {
                Toast.makeText(context,it,Toast.LENGTH_LONG).show()
                viewModel.resetError()
            }
        })

        binding.button.isEnabled = false
        binding.button.alpha = 0.2f

        binding.resetPasswordEditText.doAfterTextChanged {
            val content = it.toString()
            if(!content.matches(emailPattern.toRegex())){
                binding.resetPasswordEditText.error = "This is not a valid email!"
                binding.button.isEnabled = false
                binding.button.alpha = 0.2f
            }else{
                binding.button.isEnabled = true
                binding.button.alpha = 1f
            }
        }

        return binding.root
    }


}
