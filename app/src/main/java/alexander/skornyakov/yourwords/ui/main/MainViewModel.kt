package alexander.skornyakov.yourwords.ui.main

import alexander.skornyakov.yourwords.app.BaseApplication
import alexander.skornyakov.yourwords.app.SessionManager
import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainViewModel @Inject constructor(app: BaseApplication): ViewModel(){
    private val _mAuth: MutableLiveData<FirebaseAuth> = MutableLiveData<FirebaseAuth>(FirebaseAuth.getInstance())
    val mAuth : LiveData<FirebaseAuth>
        get() = _mAuth

//    private var _hideTitlebar = MutableLiveData<Boolean>(true)
//    val hideTitlebar : MutableLiveData<Boolean>
//        get() = _hideTitlebar

//    fun showTitlebar(){
//        _hideTitlebar.value = false
//    }
//
//    fun hideTitlebar(){
//        _hideTitlebar.value = true
//    }

    private var _signOut: MutableLiveData<Boolean> = MutableLiveData(false)
    val signOut: MutableLiveData<Boolean>
        get() = _signOut

    fun signOut(){
        _signOut.value = true
    }
//
//    fun signOutCompleted(){
//        _signOut.value = false
//    }

//    private val _drawerLocked : MutableLiveData<Boolean> = MutableLiveData(true)
//    val drawerLocked : MutableLiveData<Boolean>
//        get() = _drawerLocked

//    fun lockDrawer(){
//        _drawerLocked.value = true
//    }
//
//    fun unlockDrawer(){
//        _drawerLocked.value = false
//    }

    @Inject lateinit var sessionManager: SessionManager

    //private val _username = MediatorLiveData<String>()
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()



//
//    //private val _username = MutableLiveData<String>("")
//    val username : MutableLiveData<String>
//            get() = sessionManager.getUser().value.data.displayName
//
//    //private val _email = MutableLiveData<String>("")
//    val email : MutableLiveData<String>
//        get() = _email
//
//    init{
//        this.mAuth.value?.addAuthStateListener{
//            if(it.currentUser!=null){
//                it.currentUser?.let {
//                    this._username.value = it?.displayName
//                    this._email.value = it?.email
//                }
//            }
//            else{
//                this.username.value = ""
//                this.email.value = ""
//            }
//        }

//    }

}
