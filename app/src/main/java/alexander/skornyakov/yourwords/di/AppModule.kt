package alexander.skornyakov.yourwords.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Module
    companion object{

        @JvmStatic
        @Provides
        fun provideFirebaseAuth(): FirebaseAuth{
            return FirebaseAuth.getInstance()
        }
    }
}