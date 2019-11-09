package alexander.skornyakov.yourwords.ui.auth.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpViewModel(val app: Application, val auth: FirebaseAuth) : AndroidViewModel(app) {
    var _email_edit = MutableLiveData<String>()
    var _name_edit = MutableLiveData<String>()
    var _password_edit1 = MutableLiveData<String>()
    var _password_edit2 = MutableLiveData<String>()

    var registered = MutableLiveData<Boolean>(false)
    var error = MutableLiveData<String>()

    fun signUp(){
        _signUpAction.value = true
    }

    private val _signUpAction = MutableLiveData<Boolean>(false)
    val signUpAction: LiveData<Boolean>
        get() = _signUpAction

    fun signUpActionComplete(){
        _signUpAction.value = false
    }

    fun signUpWithEmail(login: String, password: String, name: String){
        auth.createUserWithEmailAndPassword(login,password).addOnCompleteListener{signUpTask->
            if(signUpTask.isSuccessful){
                val user = auth.currentUser
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user?.updateProfile(profileUpdate)
                    ?.addOnCompleteListener{updateTask->
                        if(updateTask.isSuccessful){
                            auth.signOut() //relogin to refresh the drawer user info
                            auth.signInWithEmailAndPassword(login,password).addOnCompleteListener{loginTask->
                                if(loginTask.isSuccessful){
                                    Log.i("AUTH", "User {name} created")
                                    registered.value = true
                                }else{
                                    error.value = loginTask.exception.toString()

                                }
                                signUpActionComplete()
                            }
                        }else{
                            error.value = updateTask.exception.toString()
                        }
                        signUpActionComplete()
                    }
            }else{
                error.value = signUpTask.exception.toString()
                signUpActionComplete()
            }
        }
    }


}
