package alexander.skornyakov.yourwords.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class MainViewModel(val auth: FirebaseAuth, app: Application): AndroidViewModel(app) {
    private var _username = MutableLiveData(auth.currentUser?.displayName)
    val username:MutableLiveData<String?>
        get() = _username

    fun changeUsername(username: String?){
        _username.value = username
    }
}
