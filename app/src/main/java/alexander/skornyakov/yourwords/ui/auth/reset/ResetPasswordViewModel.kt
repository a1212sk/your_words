package alexander.skornyakov.yourwords.ui.auth.reset

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordViewModel(val app : Application, val auth : FirebaseAuth) : AndroidViewModel(app) {

    val email = MutableLiveData<String>()

    private val _emailSent : MutableLiveData<Boolean> = MutableLiveData(false)
    val emailSent: LiveData<Boolean>
        get() = _emailSent

    fun resetEmailSent(){
        _emailSent.value = false
    }

    fun resetError(){
        _error.value = ""
    }

    private val _error : MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    fun resetPassword(){
        auth.sendPasswordResetEmail(email.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                Log.i("ResetPassword","success")
                _emailSent.value = true
            }else{
                Log.i("ResetPassword","failed")
                _error.value = "Error!"
            }
        }
    }

}
