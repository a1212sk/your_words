package alexander.skornyakov.yourwords.ui.auth.signin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class SignInViewModelFactory (
    private val auth: FirebaseAuth, private val app: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(auth, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}