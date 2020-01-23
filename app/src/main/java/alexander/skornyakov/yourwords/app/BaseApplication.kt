package alexander.skornyakov.yourwords.app

import alexander.skornyakov.yourwords.di.DaggerAppComponent
import alexander.skornyakov.yourwords.ui.auth.AuthActivity
import alexander.skornyakov.yourwords.ui.auth.AuthResource
import alexander.skornyakov.yourwords.ui.main.MainActivity
import android.content.Intent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class BaseApplication: DaggerApplication() {

    @Inject lateinit var sessionManager: SessionManager

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().app(this).build()
    }

    override fun onCreate() {
        super.onCreate()
//        if (sessionManager.getUser().value?.status==AuthResource.AuthStatus.NOT_AUTHENTICATED){
//            val intent = Intent(applicationContext,AuthActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
//        else{
//            val intent = Intent(applicationContext,MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }

    }
}