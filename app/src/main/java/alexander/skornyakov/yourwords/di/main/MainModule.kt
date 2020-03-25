package alexander.skornyakov.yourwords.di.main

import alexander.skornyakov.yourwords.app.BaseApplication
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class MainModule {

    private lateinit var tts: TextToSpeech

    lateinit var applicationContext: BaseApplication

    @Provides
    fun provideTTS(applicationContext: BaseApplication): TextToSpeech{
        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            tts.language = Locale.US
        })
        return tts
    }

}