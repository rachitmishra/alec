package `in`.ceeq.lyte

import `in`.ceeq.lyte.data.room.LyteDatabase
import `in`.ceeq.lyte.utils.AnalyticsUtils
import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp
import sun.security.krb5.internal.KDCOptions.with






class LyteApplication : Application() {

    val db = Room.databaseBuilder(this, LyteDatabase::class.java, "lyte-db").build()

    override fun onCreate() {
        super.onCreate()
        AnalyticsUtils().init(this)
        Fresco.initialize(this)
        FirebaseApp.initializeApp(this)
        Fabric.with(this, Crashlytics())
    }
}
