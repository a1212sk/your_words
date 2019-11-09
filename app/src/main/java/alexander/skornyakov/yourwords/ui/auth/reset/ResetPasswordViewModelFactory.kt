package alexander.skornyakov.yourwords.ui.auth.reset

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordViewModelFactory(val app : Application, val auth: FirebaseAuth) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)) {
            return ResetPasswordViewModel(app, auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}