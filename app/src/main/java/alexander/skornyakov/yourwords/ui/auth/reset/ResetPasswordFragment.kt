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
import alexander.skornyakov.yourwords.util.Utils
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.app.Application
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.reset_password_fragment.*
import javax.inject.Inject

class ResetPasswordFragment @Inject constructor() : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var mAuth: FirebaseAuth
    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ResetPasswordViewModel::class.java)

        val binding = DataBindingUtil.inflate<ResetPasswordFragmentBinding>(
            inflater,
            R.layout.reset_password_fragment,
            container,
            false
        )
        binding.viewModel = viewModel

        viewModel.emailSent.observe(this, Observer {
            if (it) {
                Toast.makeText(context, "Check your mail!", Toast.LENGTH_LONG).show()
                viewModel.resetEmailSent()
                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragment2ToSignInFragment2())
                Utils.hideKeyboard(activity!!)
            }
        })

        viewModel.error.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.resetError()
            }
        })

        Utils.disableButton(binding.resetButton)

        binding.resetPasswordEditText.doAfterTextChanged {
            val content = it.toString()
            if (!content.matches(emailPattern.toRegex())) {
                binding.resetPasswordEditText.error = getString(R.string.invalid_email)
                Utils.disableButton(reset_button)
            } else {
                Utils.enableButton(binding.resetButton)
            }
        }

        return binding.root
    }


}
