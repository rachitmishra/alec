package in.ceeq.alec.databinding;

import android.databinding.BindingAdapter;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import in.ceeq.alec.R;
import in.ceeq.alec.utils.ui.LetterTileDrawable;

public class ImageViewBindingAttribute {

    @BindingAdapter({"glide", "error"})
    public static void bindGlide(final ImageView view, final String url, String error) {

        LetterTileDrawable letterTileDrawable = new LetterTileDrawable(view.getContext())
                .setIsCircular(true)
                .setLetter(error.charAt(0));
        if (!TextUtils.isEmpty(url)) {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(view.getContext()).load(url).error(letterTileDrawable).into(view);
        } else {
            view.setImageDrawable(letterTileDrawable);
        }
    }

    @BindingAdapter({"glide"})
    public static void bindGlide(final ImageView view, final String url) {

        VectorDrawableCompat personDrawable =
                VectorDrawableCompat.create(view.getResources(),
                        R.drawable.ic_person_white_24dp, null);

        if (!TextUtils.isEmpty(url)) {
            Glide.with(view.getContext()).load(url).error(personDrawable).into(view);
        }
    }
}
