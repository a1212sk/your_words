package alexander.skornyakov.yourwords.ui.auth

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.AuthFragmentBinding
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


class AuthFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
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


        //authViewModel and layout init
        val authViewModelFactory = AuthViewModelFactory(mAuth,app)
        viewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel::class.java)
        val binding = DataBindingUtil.inflate<AuthFragmentBinding>(inflater, R.layout.auth_fragment, container,false)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this

        //Sign in button clicked
        viewModel.signinAction.observe(this, Observer{
            GlobalScope.launch(Dispatchers.Main) {
                if(it==true){
                    if(isInternetAvailable()) {
                        auth()
                        viewModel.completeSigninAction()
                    }
                    else{
                        Toast.makeText(context,"Check your internet connection!",Toast.LENGTH_LONG).show()
                    }
                }
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
                    Utils.hideKeyboard(activity!!)
                    mainViewModel.showTitlebar()
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToSetsFragment())
                } else {
                    Toast.makeText(context, "Wrong password or username!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun isInternetAvailable(): Boolean = runBlocking {
        try {
            if(isNetworkAvailable()) {
                val address = withContext(Dispatchers.IO) { InetAddress.getByName("www.google.com") }
                !address.equals("")
            }
            else{
                false
            }
        } catch (e: UnknownHostException) {
            false
        }
    }


}
