package alexander.skornyakov.yourwords

import alexander.skornyakov.yourwords.databinding.MainActivityBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        Log.d("SS----","firebase must be initialized...")
        val binding = DataBindingUtil.inflate<MainActivityBinding>(
            layoutInflater,
            R.layout.main_activity,
            container,
            false)
        setContentView(binding.root)

    }

}
