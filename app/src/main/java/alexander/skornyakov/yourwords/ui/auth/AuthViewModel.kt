package alexander.skornyakov.yourwords.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(val auth: FirebaseAuth, app: Application) : AndroidViewModel(app) {
    private var _username = MutableLiveData(auth.currentUser?.displayName)
    val username: MutableLiveData<String?>
        get() = _username

    fun changeUsername(username: String?){
        _username.value = username
    }
}
