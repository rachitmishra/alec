package `in`.ceeq.lyte.databinding


import android.content.Context
import android.content.Intent
import android.view.View
import java.net.MalformedURLException

class ContactEditViewModel(context: Context) : BaseContactViewModel(context) {

    private val mOnContactAddEditListener
            = context as BaseContactViewModel.OnContactAddEditListener

    fun onSaveClick() {
        val names = name?.let {
            val names = it.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            firstName = names[0]
            lastName = names[1]
        }
        if (id > 0) {
            putContact(mOnContactAddEditListener)
        } else {
            postContact(mOnContactAddEditListener)
        }
    }


    fun onPhotoSelectClick(view: View) {
    }

    @Throws(MalformedURLException::class)
    fun handlePhotoSelection(data: Intent) {
//        val selectedImagePaths = GalleryActivity.getSelectedImagePath(data)
//        if (!selectedImagePaths.isEmpty() && !TextUtils.isEmpty(selectedImagePaths[0])) {
//            val uri = Uri.fromFile(File(selectedImagePaths[0])).toString()
//            val url = URL(uri).toString()
//            profilePicUrl = url
//        }
    }
}

