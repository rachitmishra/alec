package `in`.ceeq.lyte.provider.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class ContactProvider : ContentProvider() {

    private var mDatabaseHelper: ContactDatabaseHelper? = null

    private var mDatabase: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        mDatabaseHelper = ContactDatabaseHelper(context!!)

        sUriMatcher.addURI(ContactContract.AUTHORITY, ContactContract.Contacts.path, CONTACTS_MATCH)

        return mDatabaseHelper != null
    }

    override fun getType(uri: Uri): String? {

        val match = sUriMatcher.match(uri)
        when (match) {
            CONTACTS_MATCH -> return ContactContract.Contacts.contentType
            else -> throw IllegalArgumentException("NO FOUND: " + uri)
        }
    }

    override fun query(uri: Uri, projection: Array<String>?,
                       selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        val mQueryBuilder = SQLiteQueryBuilder()
        mDatabase = mDatabaseHelper!!.readableDatabase
        val cursor: Cursor

        val uriType = sUriMatcher.match(uri)
        when (uriType) {
            CONTACTS_MATCH -> {
                mQueryBuilder.tables = ContactContract.Contacts.path
                cursor = mQueryBuilder.query(mDatabase, projection, selection,
                        selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri(context!!.contentResolver, uri)
                return cursor
            }
            else -> throw IllegalArgumentException("NOT FOUND: " + uri)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        mDatabase = mDatabaseHelper!!.writableDatabase
        var result: Uri? = null
        val uriType = sUriMatcher.match(uri)
        when (uriType) {
            CONTACTS_MATCH -> {
                val userRowId = mDatabase!!.insertWithOnConflict(
                        ContactContract.Contacts.path, null, values, CONFLICT_REPLACE)

                if (userRowId > 0) {
                    result = ContentUris.withAppendedId(ContactContract.Contacts.contentUri, userRowId)
                    context!!.contentResolver.notifyChange(result!!, null)
                }
            }
        }
        return result
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        mDatabase = mDatabaseHelper!!.writableDatabase
        val uriType = sUriMatcher.match(uri)
        var updatedRows = 0
        when (uriType) {
            CONTACTS_MATCH -> {
                updatedRows = mDatabase!!.update(ContactContract.Contacts.path,
                        values, selection, selectionArgs)
                if (updatedRows > 0) {
                    context!!.contentResolver.notifyChange(uri, null)
                }
            }
        }

        return updatedRows
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun bulkInsert(uri: Uri, values: Array<ContentValues>): Int {
        mDatabase = mDatabaseHelper!!.writableDatabase
        var numInserted = 0
        var table: String? = null
        val uriType = sUriMatcher.match(uri)
        when (uriType) {
            CONTACTS_MATCH -> table = ContactContract.Contacts.path
        }
        mDatabase!!.beginTransaction()
        try {
            for (contentValues in values) {
                val newID = mDatabase!!.insertWithOnConflict(
                        table, null, contentValues, CONFLICT_REPLACE)
                if (newID <= 0) {
                    throw SQLException("Failed to insert row into " + uri)
                }
            }
            mDatabase!!.setTransactionSuccessful()
            context!!.contentResolver.notifyChange(uri, null)
            numInserted = values.size
        } finally {
            mDatabase!!.endTransaction()
        }
        return numInserted
    }

    companion object {

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        private val CONTACTS_MATCH = 7004
    }
}
