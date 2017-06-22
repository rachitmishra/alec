package `in`.ceeq.alec

import `in`.ceeq.alec.utils.AnalyticsUtils
import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp


class AlecApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AnalyticsUtils().init(this)
        Fresco.initialize(this)
        FirebaseApp.initializeApp(this)
    }
}
