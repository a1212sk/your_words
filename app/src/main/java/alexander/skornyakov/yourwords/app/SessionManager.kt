package alexander.skornyakov.yourwords.app

import alexander.skornyakov.yourwords.ui.auth.AuthResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    fun getUser()=user

}