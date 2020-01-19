package alexander.skornyakov.yourwords.ui.auth.signup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.databinding.SignUpFragmentBinding
import alexander.skornyakov.yourwords.ui.auth.AuthResource
import alexander.skornyakov.yourwords.ui.auth.signin.SignInFragmentDirections
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.ui.main.MainViewModelFactory
import alexander.skornyakov.yourwords.util.Utils
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject

class SignUpFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var viewModel: SignUpViewModel
    @Inject lateinit var sessionManager: SessionManager
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SignUpViewModel::class.java)

        val binding = DataBindingUtil.inflate<SignUpFragmentBinding>(
            inflater,
            R.layout.sign_up_fragment,
            container,
            false
        )
        binding.signUpViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.signUpAction.observe(this, Observer {
            if (it == true) {
                if (Utils.isInternetAvailable(context!!)) {
                    val fields = listOf(
                        binding.emailTextEdit,
                        binding.loginTextEdit,
                        binding.passwordTextEdit,
                        binding.passwordTextEdit2
                    )
                    if (fields.any { it.text.isNullOrEmpty() || !it.error.isNullOrEmpty() }) {
                        Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val email = viewModel._email_edit.value!!
                        val pass = viewModel._password_edit1.value!!
                        val name = viewModel._name_edit.value!!
                        Utils.disableButton(sign_up_button)
                        viewModel.signUpWithEmail(email, pass, name)
                    }

                } else {
                    Toast.makeText(context, getString(R.string.check_internet), Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Utils.enableButton(sign_up_button)
            }
        })

        binding.emailTextEdit.doAfterTextChanged {
            val content = it.toString()
            if (!content.matches(emailPattern.toRegex())) {
                binding.emailTextEdit.error = getString(R.string.invalid_email)
            }
        }

        binding.loginTextEdit.doAfterTextChanged {
            val content = it.toString()
            if (content.isNullOrEmpty()) {
                binding.loginTextEdit.error = "Empty name!"
            }
        }

        binding.passwordTextEdit.doAfterTextChanged {
            val content = it.toString()
            if (content.length < 6) {
                binding.passwordTextEdit.error = getString(R.string.pass_less_than_six)
            }
        }

        binding.passwordTextEdit2.doAfterTextChanged {
            val content = it.toString()
            val password = binding.passwordTextEdit.text.toString()
            if (!content.equals(password)) {
                binding.passwordTextEdit2.error = "Difference!"
            }
        }

//        viewModel.registered.observe(this, Observer {
//            if (it == true) {
//                activity?.let {
//                    Utils.hideKeyboard(it)
//                }
//                //TODO go to main
//                Toast.makeText(context, "Go to main", Toast.LENGTH_LONG).show()
//            }
//        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        sessionManager.getUser().observe(this, Observer {
            when(it.status){
                AuthResource.AuthStatus.AUTHENTICATED->{
                    //TODO go to main
                    Toast.makeText(context,"Registered. GO to main",Toast.LENGTH_LONG).show()
                }
                AuthResource.AuthStatus.ERROR->{
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                }
            }
        })

        return binding.root
    }


}
