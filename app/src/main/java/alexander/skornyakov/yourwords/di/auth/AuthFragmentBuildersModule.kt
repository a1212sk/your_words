package alexander.skornyakov.yourwords.di.auth

import alexander.skornyakov.yourwords.ui.auth.reset.ResetPasswordFragment
import alexander.skornyakov.yourwords.ui.auth.signin.SignInFragment
import alexander.skornyakov.yourwords.ui.auth.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule{

    @ContributesAndroidInjector
    abstract fun contributeSignInFragment():SignInFragment

    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment():SignUpFragment

    @ContributesAndroidInjector
    abstract fun contributeResetPasswordFragment():ResetPasswordFragment

}