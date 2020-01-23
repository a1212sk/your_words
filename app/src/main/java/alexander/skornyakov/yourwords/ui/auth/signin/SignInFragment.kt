package alexander.skornyakov.yourwords.ui.auth.signin

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.databinding.SignInFragmentBinding
import alexander.skornyakov.yourwords.ui.auth.AuthResource
import alexander.skornyakov.yourwords.ui.main.MainActivity
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.util.Utils
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.*
import androidx.lifecycle.Observer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sign_in_fragment.*
import javax.inject.Inject

class SignInFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var viewModel: SignInViewModel
    @Inject lateinit var application: BaseApplication
    @Inject lateinit var firebaseAuth: FirebaseAuth
    @Inject lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(firebaseAuth.currentUser!=null){
            Toast.makeText(context,"Go to main activity",Toast.LENGTH_LONG).show()
            var intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SignInViewModel::class.java)
        val binding = DataBindingUtil.inflate<SignInFragmentBinding>(
            inflater,
            R.layout.sign_in_fragment,
            container,
            false)
        binding.signInViewModel = viewModel
        binding.lifecycleOwner = this

        //Sign in button clicked
        viewModel.signinAction.observe(viewLifecycleOwner, Observer{
            GlobalScope.launch(Dispatchers.Main) {
                if(it==true){
                    if(Utils.isInternetAvailable(context!!)) {
                        viewModel.auth()
                    }
                    else{
                        Toast.makeText(context,
                            "Check your internet connection!",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })

        //Sign up button clicked
        viewModel.signupAction.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragment2ToSignUpFragment2())
                viewModel.completeSignupAction()
            }
        })

        viewModel.resetAction.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragment2ToResetPasswordFragment2())
                viewModel.completeResetAction()
            }
        })

        subscribeObservers()

        return binding.root
    }

    private fun subscribeObservers(){
        sessionManager.getUser().observe(this, Observer {
            when(it.status){
                AuthResource.AuthStatus.AUTHENTICATED->{
                    Toast.makeText(context,"Logged In !!!",Toast.LENGTH_LONG).show()
                    Utils.disableButton(sign_in_button)
                    gotoMainActivity()
                }
                AuthResource.AuthStatus.LOADING->{
                    Toast.makeText(context,"Loading...",Toast.LENGTH_SHORT).show()
                    Utils.disableButton(sign_in_button)
                }
                AuthResource.AuthStatus.ERROR->{
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                    Utils.enableButton(sign_in_button)
                }
            }

        })
    }

    fun gotoMainActivity(){
        var intent = Intent(context,MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }



}
