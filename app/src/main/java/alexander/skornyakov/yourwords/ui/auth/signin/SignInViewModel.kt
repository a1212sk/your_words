package alexander.skornyakov.yourwords.ui.auth.signin

import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignInViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var application: BaseApplication
    @Inject lateinit var sessionManager: SessionManager

    //////////////////////////////////////////////////////////
    //                  SIGN IN ACTION                      //

    private var _signinAction = MutableLiveData(false)
    val signinAction : LiveData<Boolean>
        get() = _signinAction

    fun signIn(){
        _signinAction.value = true
    }

    fun completeSigninAction() {
        _signinAction.value = false
    }

    ///////////////////////////////////////////////////////////
    //                  RESET ACTION                      //


    private var _resetAction = MutableLiveData(false)
    val resetAction : LiveData<Boolean>
        get() = _resetAction

    fun reset(){
        _resetAction.value = true
    }

    fun completeResetAction(){
        _resetAction.value = false
    }

    ///////////////////////////////////////////////////////////
    //                  SIGN UP ACTION                      //



    private var _signupAction = MutableLiveData(false)
    val signupAction : LiveData<Boolean>
        get() = _signupAction

    fun signUp(){
        _signupAction.value = true
    }

    fun completeSignupAction() {
        _signupAction.value = false
    }

    ///////////////////////////////////////////////////////////

    //used in the layout
    var _email_edit = MutableLiveData<String>()
    //used in the layout
    var _password_edit = MutableLiveData<String>()

    fun auth(){
        val email = this._email_edit.value
        val password = this._password_edit.value
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            sessionManager.authenticate(email,password)
        }
        completeSigninAction()
    }

}
