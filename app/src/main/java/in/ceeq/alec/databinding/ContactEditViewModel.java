package in.ceeq.alec.databinding;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.ceeq.alec.gallery.GalleryActivity;

public class ContactEditViewModel extends BaseContactViewModel {

    private OnContactAddEditListener mOnContactAddEditListener;

    public ContactEditViewModel(Context context) {
        super(context);
        mOnContactAddEditListener = (OnContactAddEditListener) context;
    }

    public void onSaveClick() {
        String[] names = getName().split(" ");
        setFirstName(names[0]);
        setLastName(names[1]);
        if (getId() > 0) {
            putContact(mOnContactAddEditListener);
        } else {
            postContact(mOnContactAddEditListener);
        }
    }

    public void onPhotoSelectClick(View view) {
        GalleryActivity.startForSelect((AppCompatActivity) view.getContext());
    }

    public void handlePhotoSelection(Intent data) throws MalformedURLException {
        ArrayList<String> selectedImagePaths = GalleryActivity.getSelectedImagePath(data);
        if (!selectedImagePaths.isEmpty() && !TextUtils.isEmpty(selectedImagePaths.get(0))) {
            String uri = Uri.fromFile(new File(selectedImagePaths.get(0))).toString();
            String url = new URL(uri).toString();
            setProfilePicUrl(url);
            update(this);
        }
    }
}

