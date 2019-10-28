package alexander.skornyakov.yourwords.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class MainViewModelFactory (
    private val auth: FirebaseAuth,
    private val app: Application
) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(auth, app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}