package in.ceeq.alec.databinding;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.view.View;

import in.ceeq.alec.ContactEditActivity;
import in.ceeq.alec.R;

public class ContactViewViewModel extends BaseContactViewModel {

    private OnContactAddEditListener mOnContactAddEditListener;

    public ContactViewViewModel(Context context) {
        super(context);
        mOnContactAddEditListener = (OnContactAddEditListener) context;
    }

    public void onContactClick(View view) {
        view.getContext().startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:"+ getMobile())));
    }

    public void onMessageClick(View view) {
        Uri sms_uri = Uri.parse("smsto:" + getMobile());
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        view.getContext().startActivity(sms_intent);
    }

    public void onShareClick(Context context) {
        String shareString = getFirstName() + "\" " + getMobile();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent,
                context.getString(R.string.share_via)));
    }

    public void onFavoriteClick() {
        this.setFavorite(!this.getFavorite());

        putContact(mOnContactAddEditListener);
    }

    public void onEditClick(Context context) {
        ContactEditActivity.start(context, getId());
    }

    public static final Creator<ContactViewViewModel> CREATOR = new Creator<ContactViewViewModel>() {
        @Override
        public ContactViewViewModel createFromParcel(Parcel source) {
            return new ContactViewViewModel(source);
        }

        @Override
        public ContactViewViewModel[] newArray(int size) {
            return new ContactViewViewModel[size];
        }
    };

    private ContactViewViewModel(Parcel in) {
        super(in);
    }
}

