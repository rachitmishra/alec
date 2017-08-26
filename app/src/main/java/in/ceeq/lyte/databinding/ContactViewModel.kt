package `in`.ceeq.lyte.databinding

import `in`.ceeq.lyte.user.UserEditActivity
import `in`.ceeq.lyte.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View

class ContactViewModel : BaseContactViewModel {

    constructor(context: Context) : super(context)

    fun onContactClick(view: View) {
        view.context.startActivity(Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + mobile)))
    }

    fun onMessageClick(view: View) {
        val sms_uri = Uri.parse("smsto:" + mobile)
        val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
        view.context.startActivity(sms_intent)
    }

    fun onShareClick(context: Context) {
        val shareString = firstName + "\" " + mobile
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString)
        shareIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(shareIntent,
                context.getString(R.string.share_via)))
    }

    fun onFavoriteClick() {
        this.favorite = !this.favorite
    }

    fun onEditClick(context: Context) {
        UserEditActivity.start(context, id)
    }
}

