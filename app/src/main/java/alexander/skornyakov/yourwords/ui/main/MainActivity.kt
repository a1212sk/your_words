package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.MainActivityBinding
import alexander.skornyakov.yourwords.databinding.NavHeaderBinding
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var mAuth: FirebaseAuth
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Log.d("SS----","firebase must be initialized...")
        mAuth = FirebaseAuth.getInstance()

        val binding = DataBindingUtil.inflate<MainActivityBinding>(
            layoutInflater,
            R.layout.main_activity,
            container,
            false)

        val vmFactory = MainViewModelFactory(mAuth, application)
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        binding.mainViewModel = vm
        setContentView(binding.root)

        auth()

        setTitlebar()

        val navHeaderBinding = NavHeaderBinding.inflate(layoutInflater)
        navHeaderBinding.mainViewModel = vm
        nav_view.addHeaderView(navHeaderBinding.root)
        navHeaderBinding.lifecycleOwner = this
        navHeaderBinding.executePendingBindings()

        nav_view.setNavigationItemSelectedListener {
            val id = it.itemId
            if(id == R.id.signout_menu_item){
                mAuth.signOut()
                auth()


            }
            false
        }

    }

    private fun setTitlebar() {
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }


    private fun auth() {
        //TODO internet access checking!!!
        val user = mAuth.currentUser
        if(user==null){
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN
            )
        }
        else{
            Toast.makeText(applicationContext, user.displayName, Toast.LENGTH_LONG ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                vm.changeUsername(user?.displayName)

            } else {
                auth()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }

}
