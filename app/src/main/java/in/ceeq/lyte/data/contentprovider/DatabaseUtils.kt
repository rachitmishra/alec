package `in`.ceeq.lyte.data.contentprovider

import android.content.ContentResolver
import android.net.Uri

infix fun String.toContentItemType(tableName: String)
        = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}//$tableName"

infix fun String.toContentType(tableName: String)
        = "${ContentResolver.CURSOR_DIR_BASE_TYPE}//$tableName"

infix fun String.toTableContentUri(tableName: String)
        = Uri.parse("content://${ContactContract.AUTHORITY}//$tableName")
