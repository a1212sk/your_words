package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.MainActivityBinding
import alexander.skornyakov.yourwords.databinding.NavHeaderBinding
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
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    //TODO hide titlebar on auth fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val binding = DataBindingUtil.inflate<MainActivityBinding>(
            layoutInflater,
            alexander.skornyakov.yourwords.R.layout.main_activity,
            container,
            false)

        val vmFactory = MainViewModelFactory(application)
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        binding.mainViewModel = vm
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setDrawer()

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.setsFragment), drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        vm.hideTitlebar.observe(this, Observer {
            if(it==false){
                toolbar.visibility = View.VISIBLE
            }
            else{
                toolbar.visibility = View.GONE
            }
        })

        vm.signOut.observe(this, Observer {
            if(it==true){
                vm?.mAuth?.value?.currentUser?.let{
                    vm.mAuth.value!!.signOut()
                    navController.navigate(R.id.signInFragment)
                    vm.hideTitlebar()
                    vm.lockDrawer()
                    vm.signOutCompleted()
                }
            }
        })

        vm.drawerLocked.observe(this, Observer {
            if(it){
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            else{
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        })

    }

    //TODO set drawer items (username,email)
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

        vm.mAuth.value?.addAuthStateListener{
            if(it.currentUser!=null){
                it.currentUser?.let {
                    vm.username.value = it.displayName!!
                    vm.email.value = it.email!!
                }
            }
            else{
                vm.username.value = ""
                vm.email.value = ""
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

}
