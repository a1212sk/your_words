package alexander.skornyakov.yourwords.ui.auth.signup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.SignUpFragmentBinding
import alexander.skornyakov.yourwords.ui.auth.signin.SignInFragmentDirections
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.ui.main.MainViewModelFactory
import alexander.skornyakov.yourwords.util.Utils
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var mainViewModel: MainViewModel
    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val app = requireNotNull(this.activity).application

        //mainViewModel init
        val factory = MainViewModelFactory(app)
        mainViewModel = ViewModelProviders.of(activity!!,factory)[MainViewModel::class.java]

        val signUpViewModelFactory = SignUpViewModelFactory(mainViewModel.mAuth.value!!, app)
        viewModel = ViewModelProviders.of(this, signUpViewModelFactory).get(SignUpViewModel::class.java)

        val binding = DataBindingUtil.inflate<SignUpFragmentBinding>(inflater,R.layout.sign_up_fragment,container,false)
        binding.signUpViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.signUpAction.observe(this, Observer {
            if(it==true) {
                if (Utils.isInternetAvailable(context!!)) {
                    val fields = listOf(
                        binding.emailTextEdit,
                        binding.loginTextEdit,
                        binding.passwordTextEdit,
                        binding.passwordTextEdit2
                    )
                    if (fields.any { it.text.isNullOrEmpty() }) {
                        Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val email = viewModel._email_edit.value!!
                        val pass = viewModel._password_edit1.value!!
                        val name = viewModel._name_edit.value!!
                        binding.signUpButton.isEnabled = false
                        binding.signUpButton.alpha = 0.2f
                        viewModel.signUpWithEmail(email, pass, name)
                    }

                } else {
                    Toast.makeText(context, "Check your internet connection!", Toast.LENGTH_LONG)
                        .show()
                }
            }else{
                binding.signUpButton.isEnabled = true
                binding.signUpButton.alpha = 1f
            }
        })


        binding.emailTextEdit.doAfterTextChanged {
            val content = it.toString()
            if(!content.matches(emailPattern.toRegex())){
                binding.emailTextEdit.error = "This is not a valid email!"
            }
        }

        binding.loginTextEdit.doAfterTextChanged {
            val content = it.toString()
            if(content.isNullOrEmpty()){
                binding.loginTextEdit.error = "You have to write your name!"
            }
        }

        binding.passwordTextEdit.doAfterTextChanged {
            val content = it.toString()
            if(content.length < 6){
                binding.passwordTextEdit.error = "Password's length must be more than 6 characters!"
            }
        }

        binding.passwordTextEdit2.doAfterTextChanged {
            val content = it.toString()
            val password = binding.passwordTextEdit.text.toString()
            if(!content.equals(password)){
                binding.passwordTextEdit2.error = "Difference!"
            }
        }

        viewModel.registered.observe(this, Observer {
            if(it==true){
                activity?.let {
                    Utils.hideKeyboard(it)
                }
                mainViewModel.showTitlebar()
                mainViewModel.unlockDrawer()
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSetsFragment())
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        return binding.root
    }




}
