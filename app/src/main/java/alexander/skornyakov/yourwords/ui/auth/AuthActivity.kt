package alexander.skornyakov.yourwords.ui.auth

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.databinding.AuthActivityBinding
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.auth_activity.*


class AuthActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<AuthActivityBinding>(
            layoutInflater,
            R.layout.auth_activity,
            container,
            false
        )
        binding.lifecycleOwner = this

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}