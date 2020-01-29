package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import alexander.skornyakov.yourwords.ui.auth.AuthResource
import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class MainViewModel @Inject constructor(val app: BaseApplication, val sessionManager: SessionManager): ViewModel(){
    private val _mAuth: MutableLiveData<FirebaseAuth> = MutableLiveData<FirebaseAuth>(FirebaseAuth.getInstance())
    val mAuth : LiveData<FirebaseAuth>
        get() = _mAuth

    private var _signOut: MutableLiveData<Boolean> = MutableLiveData(false)
    val signOut: MutableLiveData<Boolean>
        get() = _signOut

    fun signOut(){
        _signOut.value = true
    }

    val email : LiveData<String> = Transformations.map(sessionManager.getUser(), ::getEmail)
    private fun getEmail(r : AuthResource<FirebaseUser>):String{
        return r.data?.email ?: ""
    }

    val username : LiveData<String> = Transformations.map(sessionManager.getUser(), ::getUser)
    private fun getUser(r : AuthResource<FirebaseUser>):String{
        return r.data?.displayName ?: ""
    }


}
