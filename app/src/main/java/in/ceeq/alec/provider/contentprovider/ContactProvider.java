package in.ceeq.alec.provider.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class ContactProvider extends ContentProvider {

    private ContactDatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mDatabase;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CONTACTS_MATCH = 7004;

    public ContactProvider() {
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new ContactDatabaseHelper(getContext());

        sUriMatcher.addURI(ContactContract.AUTHORITY, ContactContract.Contacts.PATH, CONTACTS_MATCH);

        return (mDatabaseHelper == null) ? false : true;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTACTS_MATCH:
                return ContactContract.Contacts.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("NO FOUND: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder mQueryBuilder = new SQLiteQueryBuilder();
        mDatabase = mDatabaseHelper.getReadableDatabase();
        Cursor cursor;

        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case CONTACTS_MATCH:
                mQueryBuilder.setTables(ContactContract.Contacts.PATH);
                cursor = mQueryBuilder.query(mDatabase, projection, selection,
                        selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("NOT FOUND: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        Uri result = null;
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case CONTACTS_MATCH:
                long userRowId = mDatabase.insertWithOnConflict(
                        ContactContract.Contacts.PATH, null, values, CONFLICT_REPLACE);

                if (userRowId > 0) {
                    result = ContentUris.withAppendedId(ContactContract.Contacts.CONTENT_URI,
                            userRowId);
                    getContext().getContentResolver().notifyChange(result, null);
                }
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        int uriType = sUriMatcher.match(uri);
        int updatedRows = 0;
        switch (uriType) {
            case CONTACTS_MATCH:
                updatedRows = mDatabase.update(ContactContract.Contacts.PATH,
                        values, selection, selectionArgs);
                if (updatedRows > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
        }

        return updatedRows;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        int numInserted = 0;
        String table = null;
        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case CONTACTS_MATCH:
                table = ContactContract.Contacts.PATH;
                break;
        }
        mDatabase.beginTransaction();
        try {
            for (ContentValues contentValues : values) {
                long newID = mDatabase.insertWithOnConflict(
                        table, null, contentValues, CONFLICT_REPLACE);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            mDatabase.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            mDatabase.endTransaction();
        }
        return numInserted;
    }
}
