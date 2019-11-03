package alexander.skornyakov.yourwords.ui.auth.signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModelFactory(val auth: FirebaseAuth, val app: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignUpViewModel::class.java)){
            return SignUpViewModel(app, auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}