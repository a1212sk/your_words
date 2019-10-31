package alexander.skornyakov.yourwords.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var _email_edit = MutableLiveData<String>()

    var _password_edit = MutableLiveData<String>()

    fun signUp(){

    }
}
