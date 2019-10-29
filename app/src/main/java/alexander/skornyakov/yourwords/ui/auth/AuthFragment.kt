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
import android.net.NetworkInfo


class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
        private const val RC_SIGN_IN = 123
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = FirebaseAuth.getInstance()
        val app: Application = requireNotNull(this.activity).application
        val binding = DataBindingUtil.inflate<AuthFragmentBinding>(inflater,R.layout.auth_fragment,container,false)
        val authViewModelFactory = AuthViewModelFactory(mAuth,app)
        viewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this


        binding.signInBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                auth()
            }

        }
        return binding.root
    }

    private fun auth() {

        if( !isNetworkAvailable() || !isInternetAvailable()){
            AlertDialog.Builder(context).apply {
                setTitle("Internet required!")
                setMessage("You need Internet to sign into this application!")
            }.create().show()
           return
        }

        val user = mAuth.currentUser
        if(user==null){
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), RC_SIGN_IN
            )
        }
        else{
            Toast.makeText(context, user.displayName, Toast.LENGTH_LONG ).show()
            findNavController().navigate(R.id.action_authFragment_to_setsFragment)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                viewModel.changeUsername(user?.displayName)
                findNavController().navigate(R.id.action_authFragment_to_setsFragment)
            } else {
                auth()
            }
        }
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    fun isInternetAvailable(): Boolean = runBlocking {
        try {
            val address = withContext(Dispatchers.IO){ InetAddress.getByName("www.google.com")}
            !address.equals("")
        } catch (e: UnknownHostException) {
            false
        }
    }




}
