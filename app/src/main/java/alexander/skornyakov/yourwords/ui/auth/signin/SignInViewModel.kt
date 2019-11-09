package alexander.skornyakov.yourwords.ui.auth.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel(val auth: FirebaseAuth, app: Application) : AndroidViewModel(app) {
    private var _username = MutableLiveData(auth.currentUser?.displayName)
    val username: LiveData<String?>
        get() = _username

    fun changeUsername(username: String?){
        _username.value = username
    }

    private var _signinAction = MutableLiveData(false)
    val signinAction : LiveData<Boolean?>
        get() = _signinAction

    fun signIn(){
        _signinAction.value = true
    }

    fun completeSigninAction() {
        _signinAction.value = false
    }

    private var _resetAction = MutableLiveData(false)
    val resetAction : LiveData<Boolean>
        get() = _resetAction

    fun reset(){
        _resetAction.value = true
    }

    fun completeResetAction(){
        _resetAction.value = false
    }

    private var _signupAction = MutableLiveData(false)
    val signupAction : LiveData<Boolean?>
        get() = _signupAction

    fun signUp(){
        _signupAction.value = true
    }

    fun completeSignupAction() {
        _signupAction.value = false
    }

    var _email_edit = MutableLiveData<String>()

    var _password_edit = MutableLiveData<String>()

}
