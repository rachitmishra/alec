package in.ceeq.alec.utils;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import in.ceeq.alec.BuildConfig;
import io.fabric.sdk.android.Fabric;

public class AnalyticsUtils {

    public void init(Application application) {

        // Crashlytics
        Fabric.with(application, new Crashlytics());

        if (!BuildConfig.DEBUG) {

        } else {
            // Uncomment if you want to use Stetho
            //Stetho.initializeWithDefaults(application);
        }
    }
}
