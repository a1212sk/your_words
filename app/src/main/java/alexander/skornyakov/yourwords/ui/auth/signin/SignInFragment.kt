package alexander.skornyakov.yourwords.ui.auth.signin

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.SignInFragmentBinding
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.ui.main.MainViewModelFactory
import alexander.skornyakov.yourwords.util.Utils
import android.app.Application
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.*
import java.net.InetAddress
import java.net.UnknownHostException

import android.net.ConnectivityManager
import android.content.Context
import android.net.NetworkInfo
import androidx.lifecycle.Observer

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mainViewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val app: Application = requireNotNull(this.activity).application
        mAuth = FirebaseAuth.getInstance()


        //mainViewModel init
        val factory = MainViewModelFactory(app)
        mainViewModel = ViewModelProviders.of(activity!!,factory)[MainViewModel::class.java]

        //if has already signed in go to sets
        if(mAuth.currentUser!=null){
            mainViewModel.showTitlebar()
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSetsFragment())
        }

        //signInViewModel and layout init
        val signInViewModelFactory =
            SignInViewModelFactory(mAuth, app)
        viewModel = ViewModelProviders.of(this, signInViewModelFactory).get(SignInViewModel::class.java)
        val binding = DataBindingUtil.inflate<SignInFragmentBinding>(inflater, R.layout.sign_in_fragment, container,false)
        binding.signInViewModel = viewModel
        binding.lifecycleOwner = this

        //Sign in button clicked
        viewModel.signinAction.observe(viewLifecycleOwner, Observer{
            GlobalScope.launch(Dispatchers.Main) {
                if(it==true){
                    if(Utils.isInternetAvailable(context!!)) {
                        auth()
                    }
                    else{
                        Toast.makeText(context,"Check your internet connection!",Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        //Sign up button clicked
        viewModel.signupAction.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
                    viewModel.completeSignupAction()
                }
            }
        })

        viewModel.resetAction.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToResetPasswordFragment())
                viewModel.completeResetAction()
            }
        })

        return binding.root
    }

    private fun auth() {
        val email = viewModel._email_edit.value
        val password = viewModel._password_edit.value
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    activity?.let {
                        Utils.hideKeyboard(it)
                    }
                    mainViewModel.showTitlebar()
                    mainViewModel.unlockDrawer()
                    viewModel.completeSigninAction()
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSetsFragment())
                } else {
                    Toast.makeText(context, "Wrong password or username!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}
