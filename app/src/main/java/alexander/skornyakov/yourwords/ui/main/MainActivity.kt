package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.MainActivityBinding
import alexander.skornyakov.yourwords.databinding.NavHeaderBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.main_activity.*



class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Log.d("SS----","firebase must be initialized...")


        val binding = DataBindingUtil.inflate<MainActivityBinding>(
            layoutInflater,
            alexander.skornyakov.yourwords.R.layout.main_activity,
            container,
            false)

        val vmFactory = MainViewModelFactory(application)
        vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        binding.mainViewModel = vm
        setContentView(binding.root)

        setTitlebar()

        setDrawer()

    }


    private fun setDrawer() {
        val navHeaderBinding = NavHeaderBinding.inflate(layoutInflater)
        navHeaderBinding.mainViewModel = vm
        nav_view.addHeaderView(navHeaderBinding.root)
        navHeaderBinding.lifecycleOwner = this
        navHeaderBinding.executePendingBindings()

        nav_view.setNavigationItemSelectedListener {
            val id = it.itemId
            if (id == R.id.signout_menu_item) {

                //TODO sign out
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

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

}
