package `in`.ceeq.lyte.provider.contentprovider

import android.net.Uri


object ContactContract {

    val AUTHORITY = "in.ceeq.lyte.provider"

    object Contacts : BaseColumns() {
        val path = "contacts"

        val serverId = "server_id"

        val firstName = "first_name"

        val lastName = "last_name"

        val email = "email"

        val mobile = "mobile"

        val profilePicUrl = "profile_pic_url"

        val favorite = "favorite"

        val contentType = path toContentType path

        val contentUri: Uri = path toTableContentUri path

        val defaultProjection = arrayOf(id, serverId, firstName, lastName, email, mobile, profilePicUrl, favorite)

        fun create() = "$firstName $text not null, $lastName text, $email text, $profilePicUrl text, " +
                    "$serverId $integer not null, $favorite integer default false, $mobile text unique($serverId)"

        fun drop() = super.drop(path)
    }
}
