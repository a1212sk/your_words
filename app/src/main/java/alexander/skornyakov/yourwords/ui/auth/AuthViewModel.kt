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

    private var _signinAction = MutableLiveData(false)
    val signinAction : MutableLiveData<Boolean?>
        get() = _signinAction

    fun signIn(){
        _signinAction.value = true
    }

    fun completeSigninAction() {
        _signinAction.value = false
    }

    var _email_edit = MutableLiveData<String>()

    var _password_edit = MutableLiveData<String>()

}
