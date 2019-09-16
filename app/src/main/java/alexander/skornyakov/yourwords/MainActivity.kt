package alexander.skornyakov.yourwords

import alexander.skornyakov.yourwords.databinding.MainActivityBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<MainActivityBinding>(
            layoutInflater,
            R.layout.main_activity,
            container,
            false)
        setContentView(binding.root)

    }

}
