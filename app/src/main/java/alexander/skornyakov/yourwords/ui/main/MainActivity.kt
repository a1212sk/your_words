package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.MainActivityBinding
import alexander.skornyakov.yourwords.databinding.NavHeaderBinding
import alexander.skornyakov.yourwords.ui.auth.AuthActivity
import alexander.skornyakov.yourwords.viewmodels.ViewModelProviderFactory
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity: DaggerAppCompatActivity() {

    private lateinit var vm: MainViewModel

    @Inject lateinit var viewModelFactory: ViewModelProviderFactory

   // @Inject lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val binding = DataBindingUtil.inflate<MainActivityBinding>(
            layoutInflater,
            alexander.skornyakov.yourwords.R.layout.main_activity,
            container,
            false)

        vm = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        binding.mainViewModel = vm
        binding.lifecycleOwner = this
        setContentView(binding.root)

        toolbar.visibility = View.VISIBLE
        setDrawer()
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.setsFragment), drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController,appBarConfiguration)



//        vm.hideTitlebar.observe(this, Observer {
//            if(it==false){
//                toolbar.visibility = View.VISIBLE
//            }
//            else{
//                toolbar.visibility = View.GONE
//            }
//        })

        vm.signOut.observe(this, Observer {
            if(it==true){
                vm?.mAuth?.value?.currentUser?.let{
                    vm.mAuth.value!!.signOut()
                    var intent = Intent(this,AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                    //navController.navigate(R.id.signInFragment)
                    //vm.lockDrawer()
                    //vm.signOutCompleted()
                }
            }
        })

//        vm.drawerLocked.observe(this, Observer {
//            if(it){
//                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//            }
//            else{
//                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            }
//        })

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            val id = destination.id
//            if(id == R.id.signInFragment
//                || id == R.id.signUpFragment
//                || id == R.id.resetPasswordFragment)
//            {
//                vm.hideTitlebar() //TODO remove from vm
//            }
//            else{
//                vm.showTitlebar()
//            }
//        }



    }

    private fun setDrawer() {
        val navHeaderBinding = NavHeaderBinding.inflate(layoutInflater)
        navHeaderBinding.mainViewModel = vm
        nav_view.addHeaderView(navHeaderBinding.root)
        navHeaderBinding.lifecycleOwner = this
        navHeaderBinding.executePendingBindings()

        nav_view.setNavigationItemSelectedListener {
            drawer.closeDrawers()
            val id = it.itemId
            if (id == R.id.signout_menu_item) {
                vm.mAuth?.value?.currentUser?.let {
                    vm.signOut()
                }
            }
            false
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

}
