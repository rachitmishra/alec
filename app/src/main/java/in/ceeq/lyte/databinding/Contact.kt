package `in`.ceeq.lyte.databinding


import android.content.ContentValues
import android.database.Cursor
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

import com.google.gson.annotations.SerializedName

import `in`.ceeq.lyte.BR

import `in`.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.SERVER_ID

open class Contact : BaseObservable, Parcelable {

    var id: Int = 0
    @SerializedName("name")
    private var firstName: String? = null
    @SerializedName("username")
    private var lastName: String? = null
    @SerializedName("phone")
    private var mobile: String? = null
    @SerializedName("email")
    private var email: String? = null
    @SerializedName("profile_pic")
    private var profilePicUrl: String? = null
    @SerializedName("favorite")
    private var favorite: Boolean = false
    @SerializedName("ignored")
    @get:Bindable
    var name: String? = null
        set(name) {
            field = name
            notifyPropertyChanged(BR.name)
        }

    constructor() {}

    @Bindable
    fun getMobile(): String {
        return mobile
    }

    @Bindable
    fun setMobile(mobile: String) {
        this.mobile = mobile
        notifyPropertyChanged(BR.mobile)
    }

    @Bindable
    fun getEmail(): String {
        return email
    }

    @Bindable
    fun setEmail(email: String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(firstName)
        dest.writeString(lastName)
        dest.writeString(profilePicUrl)
        dest.writeString(mobile)
        dest.writeString(email)
        dest.writeByte((if (favorite) 0 else 1).toByte())
    }

    protected constructor(`in`: Parcel) {
        firstName = `in`.readString()
        lastName = `in`.readString()
        profilePicUrl = `in`.readString()
        mobile = `in`.readString()
        email = `in`.readString()
        favorite = `in`.readByte().toInt() != 0
    }

    @Bindable
    fun getFirstName(): String {
        return firstName
    }

    @Bindable
    fun setFirstName(firstName: String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getLastName(): String {
        return lastName
    }

    @Bindable
    fun setLastName(lastName: String) {
        this.lastName = lastName
        var name = getFirstName()
        if (!TextUtils.isEmpty(getLastName())) {
            name += " " + getLastName()
        }
        name = name
        notifyPropertyChanged(BR.lastName)
    }

    @Bindable
    fun getProfilePicUrl(): String {
        return profilePicUrl
    }

    @Bindable
    fun setProfilePicUrl(profilePicUrl: String) {
        this.profilePicUrl = profilePicUrl
        notifyPropertyChanged(BR.profilePicUrl)
    }

    @Bindable
    fun getFavorite(): Boolean {
        return favorite
    }

    @Bindable
    fun setFavorite(favorite: Boolean) {
        this.favorite = favorite
        notifyPropertyChanged(BR.favorite)
    }

    fun loadFromCursor(cursor: Cursor?) {
        if (cursor != null && cursor.count > 0) {
            setFirstName(
                    cursor.getString(cursor.getColumnIndex(firstName)))
            setLastName(
                    cursor.getString(cursor.getColumnIndex(lastName)))
            setMobile(
                    cursor.getString(cursor.getColumnIndex(mobile)))
            setEmail(
                    cursor.getString(cursor.getColumnIndex(email)))
            setProfilePicUrl(
                    cursor.getString(cursor.getColumnIndex(profilePicUrl)))
            setFavorite(cursor.getInt(cursor.getColumnIndex(favorite)) != 0)
            id = cursor.getInt(cursor.getColumnIndex(SERVER_ID))
        }
    }

    val contentValues: ContentValues
        get() {
            val contentValues = ContentValues()
            contentValues.put(firstName, firstName)
            contentValues.put(lastName, lastName)
            contentValues.put(mobile, mobile)
            contentValues.put(email, email)
            contentValues.put(profilePicUrl, profilePicUrl)
            contentValues.put(SERVER_ID, id)
            contentValues.put(favorite, favorite)
            return contentValues
        }

    companion object {

        val CREATOR: Parcelable.Creator<Contact> = object : Parcelable.Creator<Contact> {
            override fun createFromParcel(source: Parcel): Contact {
                return Contact(source)
            }

            override fun newArray(size: Int): Array<Contact> {
                return arrayOfNulls(size)
            }
        }
    }
}

