package in.ceeq.lyte.utils;

import android.app.Application;


import in.ceeq.lyte.BuildConfig;

public class AnalyticsUtils {

    public void init(Application application) {

        if (BuildConfig.DEBUG) {
            // Uncomment if you want to use Stetho
            //Stetho.initializeWithDefaults(application);
        }
    }
}
