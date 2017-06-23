package `in`.ceeq.lyte.provider.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

data class User(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "first_name")
        val firstName: String,
        @ColumnInfo(name = "last_name")
        val lastName: String,
        @ColumnInfo(name = "email")
        val email: String,
        @ColumnInfo(name = "mobile")
        val mobile: String,
        @ColumnInfo(name = "profile_pic_url")
        val profilePicUrl: String,
        @ColumnInfo(name = "favorite")
        val favorite: Boolean
)
