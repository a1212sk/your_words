package alexander.skornyakov.yourwords.app

import alexander.skornyakov.yourwords.ui.auth.AuthResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(){

    private val user : MutableLiveData<AuthResource<FirebaseUser>> = MutableLiveData()
    @Inject lateinit var firebaseAuth: FirebaseAuth

    fun authenticate(email: String, password: String) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            user.value = AuthResource.loading(null)
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    user.value = AuthResource.authenticated(firebaseAuth.currentUser)
                }
                else
                {
                    user.value = AuthResource.error("Wrong credentials!",null)
                }
            }
        }
        else
        {
            user.value = AuthResource.error("Bad login or password!",null)
        }
    }

    fun signUpWithEmail(login: String, password: String, name: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener { signUpTask ->
            if (signUpTask.isSuccessful) {
                val newUser = firebaseAuth.currentUser
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                newUser?.updateProfile(profileUpdate)
                    ?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            user.value = AuthResource.authenticated(newUser)
                        } else {
                            user.value = AuthResource.error(updateTask.exception.toString(),null)
                        }
                    }
            } else {
                AuthResource.error(signUpTask.exception.toString(),null)
            }
        }
    }

    fun getUser() : LiveData<AuthResource<FirebaseUser>>{
        if (user.value==null){
            GlobalScope.launch {
                withContext(Dispatchers.Main){
                    if (firebaseAuth.currentUser!=null){
                        user.value = AuthResource.authenticated(firebaseAuth.currentUser)
                    }else{
                        user.value = AuthResource.logout()//NOT Authenticated status
                    }
                }
            }

        }
        return user
    }

    fun logOut() {
        firebaseAuth.signOut()
        user.value = AuthResource.logout()
    }

}