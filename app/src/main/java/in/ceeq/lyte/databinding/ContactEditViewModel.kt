package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.gallery.GalleryActivity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import java.io.File
import java.net.MalformedURLException
import java.net.URL

class ContactEditViewModel(context: Context) : BaseContactViewModel(context) {

    private val mOnContactAddEditListener: BaseContactViewModel.OnContactAddEditListener

    init {
        mOnContactAddEditListener = context as BaseContactViewModel.OnContactAddEditListener
    }

    fun onSaveClick() {
        val names = name.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        firstName = names[0]
        lastName = names[1]
        if (id > 0) {
            putContact(mOnContactAddEditListener)
        } else {
            postContact(mOnContactAddEditListener)
        }
    }

    fun onPhotoSelectClick(view: View) {
        GalleryActivity.startForSelect(view.context as AppCompatActivity)
    }

    @Throws(MalformedURLException::class)
    fun handlePhotoSelection(data: Intent) {
        val selectedImagePaths = GalleryActivity.getSelectedImagePath(data)
        if (!selectedImagePaths.isEmpty() && !TextUtils.isEmpty(selectedImagePaths[0])) {
            val uri = Uri.fromFile(File(selectedImagePaths[0])).toString()
            val url = URL(uri).toString()
            profilePicUrl = url
            update(this)
        }
    }
}

