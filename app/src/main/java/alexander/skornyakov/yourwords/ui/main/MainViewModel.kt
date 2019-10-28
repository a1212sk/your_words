package alexander.skornyakov.yourwords.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel(val auth: FirebaseAuth, app: Application): AndroidViewModel(app) {
    val username = "User Name"
}
