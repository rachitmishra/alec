package `in`.ceeq.lyte.databinding

import `in`.ceeq.lyte.utils.ui.LetterTileDrawable
import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView

object ImageViewBindingAdapter {

    @BindingAdapter("fresco", "error", requireAll = false)
    @JvmStatic fun bindPhotoManager(view: SimpleDraweeView, url: String?, error: String?) {

        val letterTileDrawable = LetterTileDrawable(view.context)
                .setIsCircular(true)
                .setLetter(error?.let{ it[0] })

        view.scaleType = ImageView.ScaleType.CENTER_CROP
        view.hierarchy.setFailureImage(letterTileDrawable)
        url?.let {
            val uri = Uri.parse(url)
            view.setImageURI(uri.toString())
        }
    }
}
