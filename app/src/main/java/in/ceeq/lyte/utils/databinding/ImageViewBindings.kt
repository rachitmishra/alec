package `in`.ceeq.lyte.utils.databinding

import `in`.ceeq.lyte.utils.ui.LetterTileDrawable
import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView

object ImageViewBindings {

    @BindingAdapter("fresco", "error", "tiled", requireAll = false)
    @JvmStatic
    fun bindPhotoManager(view: SimpleDraweeView, url: String?, error: String?, tiled: Boolean) {

        if (tiled) {
            val letterTileDrawable = LetterTileDrawable(view.context)
                    .setIsCircular(true)
                    .setLetter(error?.let { it[0] })
            view.hierarchy.setFailureImage(letterTileDrawable)
        }

        view.scaleType = ImageView.ScaleType.CENTER_CROP
        url?.let {
            val uri = Uri.parse(url)
            view.setImageURI(uri.toString())
        }
    }
}
