package in.ceeq.alec.utils;

import in.ceeq.alec.BuildConfig;

public class LogUtils {

    public static void logException(Exception e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        } else {
            // log to remote tracker
        }
    }
}
