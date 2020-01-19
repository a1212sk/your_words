package alexander.skornyakov.yourwords.di.auth

import alexander.skornyakov.yourwords.di.ViewModelKey
import alexander.skornyakov.yourwords.ui.auth.reset.ResetPasswordViewModel
import alexander.skornyakov.yourwords.ui.auth.signin.SignInViewModel
import alexander.skornyakov.yourwords.ui.auth.signup.SignUpViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelsModule{

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResetPasswordViewModel::class)
    abstract fun bindResetPasswordViewModel(viewModel: ResetPasswordViewModel):ViewModel
}