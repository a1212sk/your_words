package alexander.skornyakov.yourwords.ui.auth

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.AuthFragmentBinding
import alexander.skornyakov.yourwords.ui.main.MainActivity
import alexander.skornyakov.yourwords.ui.main.MainViewModel
import alexander.skornyakov.yourwords.ui.main.MainViewModelFactory
import alexander.skornyakov.yourwords.util.Utils
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

import kotlinx.coroutines.*
import java.net.InetAddress
import java.net.UnknownHostException

import android.net.ConnectivityManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer


class AuthFragment : Fragment() {

    companion object {
        //fun newInstance() = AuthFragment()
        private const val RC_SIGN_IN = 123
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mainViewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val app: Application = requireNotNull(this.activity).application

        //mainViewModel init
        val factory = MainViewModelFactory(app)
        mainViewModel = ViewModelProviders.of(activity!!,factory)[MainViewModel::class.java]

        mAuth = FirebaseAuth.getInstance()
        val binding = DataBindingUtil.inflate<AuthFragmentBinding>(inflater,R.layout.auth_fragment,container,false)
        val authViewModelFactory = AuthViewModelFactory(mAuth,app)
        viewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.signinAction.observe(this, Observer{
            GlobalScope.launch(Dispatchers.Main) {
                if(it==true){
                    val email = viewModel._email_edit.value
                    val password = viewModel._password_edit.value
                    if(!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Utils.hideKeyboard(activity!!)
                               // val user = mAuth.currentUser
                                mainViewModel.showTitlebar()
//                                Toast.makeText(context, user?.displayName, Toast.LENGTH_LONG).show()
                                //findNavController().popBackStack(R.id.setsFragment,true)
                                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToSetsFragment())
                                viewModel.completeSigninAction()

                            } else {
                                Toast.makeText(
                                    context,
                                    "Wrong password or username!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                   // auth()
                   // viewModel.completeSigninAction()
                }
            }
        })
        return binding.root
    }


//    private fun auth() {
//
//        if( !isNetworkAvailable() || !isInternetAvailable()){
//            AlertDialog.Builder(context).apply {
//                setTitle("Internet required!")
//                setMessage("You need Internet to sign into this application!")
//            }.create().show()
//           return
//        }
//
//        val user = mAuth.currentUser
//        if(user==null){
//            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
//            startActivityForResult(
//                AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .setAvailableProviders(providers)
//                    .build(), RC_SIGN_IN
//            )
//        }
//        else{
//            Toast.makeText(context, user.displayName, Toast.LENGTH_LONG ).show()
//            findNavController().navigate(R.id.action_authFragment_to_setsFragment)
//        }
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in
//                val user = FirebaseAuth.getInstance().currentUser
//                viewModel.changeUsername(user?.displayName)
//                findNavController().navigate(R.id.action_authFragment_to_setsFragment)
//            } else {
//                auth()
//            }
//        }
//    }

    //TODO check internet
    private fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    private fun isInternetAvailable(): Boolean = runBlocking {
        try {
            val address = withContext(Dispatchers.IO){ InetAddress.getByName("www.google.com")}
            !address.equals("")
        } catch (e: UnknownHostException) {
            false
        }
    }


}
