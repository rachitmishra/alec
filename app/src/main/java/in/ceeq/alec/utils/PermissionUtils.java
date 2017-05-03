package in.ceeq.alec.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import in.ceeq.alec.R;

public class PermissionUtils {

    public static final int REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_CODE_SETTINGS = 9999;

    /**
     * Return true if the permission available, else starts permission request from user
     *
     * @param activity   Source Activity
     * @param permission Permission for which Snackbar has to be shown,
     *                   helps in deciding the message string for Snackbar
     */
    public static boolean requestPermission(@NonNull final Activity activity,
                                            @NonNull final String permission) {

        if (checkSelfPermission(activity, permission)) {
            return true;
        } else {
            int requestCode = REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE;

            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    requestCode);
            return false;
        }
    }

    private static boolean checkSelfPermission(@NonNull final Activity activity,
                                               @NonNull final String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @param activity Context where the Settings screen will open
     */
    public static void openSettingsScreen(final Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null); // NON-NLS
        intent.setData(uri);
        activity.startActivityForResult(intent, REQUEST_CODE_SETTINGS);
    }

    /**
     * @param activity   Context where the Snackbar will be shown
     * @return snackbar snackbar instance which can be useful to set callbacks,if needed
     */
    public static Snackbar showPermissionDeclineMessage(@NonNull final Activity activity) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                R.string.message_permission_denied_external_storage, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.button_settings, new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                openSettingsScreen(activity);
            }
        });
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.colorAccent)).show();
        return snackbar;
    }
}
