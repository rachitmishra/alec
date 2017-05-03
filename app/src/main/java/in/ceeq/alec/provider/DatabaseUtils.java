package in.ceeq.alec.provider;

import android.content.ContentResolver;
import android.net.Uri;


public class DatabaseUtils {
    public static String getTableContentItemType(String mTableName) {
        return new StringBuilder(ContentResolver.CURSOR_ITEM_BASE_TYPE).append("/").append(mTableName).toString();
    }

    public static String getTableContentType(String mTableName) {
        return new StringBuilder(ContentResolver.CURSOR_DIR_BASE_TYPE).append("/").append(mTableName).toString();
    }

    public static Uri getTableContentUri(String mTableName) {
        return Uri.parse("content://" + ContactContract.AUTHORITY + "/" + mTableName);
    }
}
