package in.ceeq.alec.databinding;

import android.databinding.BindingAdapter;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;

import in.ceeq.alec.R;

public class ImageViewBindingAttribute {

    @BindingAdapter({"glide", "error"})
    public static void bindGlide(final ImageView view, final String url, String error) {

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();

        TextDrawable drawable1 = TextDrawable.builder()
                .buildRound(error.substring(0, 1).toUpperCase(), color1);

        if (!TextUtils.isEmpty(url)) {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(view.getContext()).load(url).error(drawable1).into(view);
        } else {
            view.setImageDrawable(drawable1);
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
