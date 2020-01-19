package alexander.skornyakov.yourwords.ui.auth.signup

import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class SignUpViewModel
@Inject constructor(
    val app: BaseApplication,
    val auth: FirebaseAuth,
    val sessionManager: SessionManager
) : ViewModel() {

    var _email_edit = MutableLiveData<String>()
    var _name_edit = MutableLiveData<String>()
    var _password_edit1 = MutableLiveData<String>()
    var _password_edit2 = MutableLiveData<String>()

    var registered = MutableLiveData<Boolean>(false)
    var error = MutableLiveData<String>()

    ////////////////////////////////////////////////////////////////////////
    //                      SIGN UP ACTION                                //

    fun signUp() {
        _signUpAction.value = true
    }

    private val _signUpAction = MutableLiveData<Boolean>(false)
    val signUpAction: LiveData<Boolean>
        get() = _signUpAction

    private fun signUpActionComplete() {
        _signUpAction.value = false
    }

    ////////////////////////////////////////////////////////////////////////


    fun signUpWithEmail(login: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(login, password).addOnCompleteListener { signUpTask ->
            if (signUpTask.isSuccessful) {
                val user = auth.currentUser
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user?.updateProfile(profileUpdate)
                    ?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            sessionManager.authenticate(login, password)
                        } else {
                            error.value = updateTask.exception.toString()
                        }
                        signUpActionComplete()
                    }
            } else {
                error.value = signUpTask.exception.toString()
                signUpActionComplete()
            }
        }
    }

}
