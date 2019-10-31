package alexander.skornyakov.yourwords.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val _mAuth: MutableLiveData<FirebaseAuth> = MutableLiveData<FirebaseAuth>(FirebaseAuth.getInstance())
    val mAuth : LiveData<FirebaseAuth>
        get() = _mAuth

    private var _hideTitlebar = MutableLiveData<Boolean>(true)
    val hideTitlebar : MutableLiveData<Boolean>
        get() = _hideTitlebar

    fun showTitlebar(){
        _hideTitlebar.value = false
    }

    fun hideTitlebar(){
        _hideTitlebar.value = true
    }

    private var _signOut: MutableLiveData<Boolean> = MutableLiveData(false)
    val signOut: MutableLiveData<Boolean>
        get() = _signOut

    fun signOut(){
        _signOut.value = true
    }

    fun signOutCompleted(){
        _signOut.value = false
    }

}
