package in.ceeq.alec.utils;

import android.app.Application;


import in.ceeq.alec.BuildConfig;

public class AnalyticsUtils {

    public void init(Application application) {

        if (BuildConfig.DEBUG) {
            // Uncomment if you want to use Stetho
            //Stetho.initializeWithDefaults(application);
        }
    }
}
