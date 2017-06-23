package in.ceeq.lyte.databinding;


import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import in.ceeq.lyte.BR;

import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.EMAIL;
import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.FAVORITE;
import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.FIRST_NAME;
import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.LAST_NAME;
import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.MOBILE;
import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.PROFILE_PIC_URL;
import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.SERVER_ID;

public class Contact extends BaseObservable implements Parcelable {

    private int id;
    @SerializedName("name")
    private String firstName;
    @SerializedName("username")
    private String lastName;
    @SerializedName("phone")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("profile_pic")
    private String profilePicUrl;
    @SerializedName("favorite")
    private boolean favorite;
    @SerializedName("ignored")
    private String name;

    public Contact() {
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    @Bindable
    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(profilePicUrl);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeByte((byte)(favorite ? 0 : 1));
    }

    protected Contact(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        profilePicUrl = in.readString();
        mobile = in.readString();
        email = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    @Bindable
    public void setLastName(String lastName) {
        this.lastName = lastName;
        String name = getFirstName();
        if (!TextUtils.isEmpty(getLastName())) {
            name += " " + getLastName();
        }
        setName(name);
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    @Bindable
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
        notifyPropertyChanged(BR.profilePicUrl);
    }

    @Bindable
    public boolean getFavorite() {
        return favorite;
    }

    @Bindable
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        notifyPropertyChanged(BR.favorite);
    }

    public void loadFromCursor(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            setFirstName(
                    cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
            setLastName(
                    cursor.getString(cursor.getColumnIndex(LAST_NAME)));
            setMobile(
                    cursor.getString(cursor.getColumnIndex(MOBILE)));
            setEmail(
                    cursor.getString(cursor.getColumnIndex(EMAIL)));
            setProfilePicUrl(
                    cursor.getString(cursor.getColumnIndex(PROFILE_PIC_URL)));
            setFavorite(cursor.getInt(cursor.getColumnIndex(FAVORITE)) != 0);
            setId(cursor.getInt(cursor.getColumnIndex(SERVER_ID)));
        }
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, firstName);
        contentValues.put(LAST_NAME, lastName);
        contentValues.put(MOBILE, mobile);
        contentValues.put(EMAIL, email);
        contentValues.put(PROFILE_PIC_URL, profilePicUrl);
        contentValues.put(SERVER_ID, id);
        contentValues.put(FAVORITE, favorite);
        return contentValues;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}

