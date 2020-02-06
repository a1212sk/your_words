package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.app.SessionManager
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
    @Inject lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        vm.signOut.observe(this, Observer {
            if(it==true){
                    sessionManager.logOut()
                    var intent = Intent(this,AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
            }
        })
    }

    private fun setDrawer() {
        val navHeaderBinding = DataBindingUtil.bind<NavHeaderBinding>(nav_view.getHeaderView(0))
        navHeaderBinding?.mainViewModel = vm
        //nav_view.addHeaderView(navHeaderBinding.root)
        navHeaderBinding?.lifecycleOwner = this
        navHeaderBinding?.executePendingBindings()

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
