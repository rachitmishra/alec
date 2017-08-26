package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.BR
import android.content.ContentValues
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import com.google.gson.annotations.SerializedName

open class Contact : BaseObservable {

    var id: Int = 0
    @SerializedName("name")
    var firstName: String? = null
        @Bindable
        get():String? {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.firstName)
        }
    @SerializedName("username")
    var lastName: String? = null
        @Bindable
        get():String? {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.lastName)
        }
    @SerializedName("phone")
    var mobile: String? = null
        @Bindable
        get():String? {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.mobile)
        }
    @SerializedName("email")
    var email: String? = null
        @Bindable
        get():String? {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.email)
        }
    @SerializedName("profile_pic")
    var profilePicUrl: String? = null
        @Bindable
        get():String? {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.profilePicUrl)
        }
    @SerializedName("favorite")
    var favorite: Boolean = false
        @Bindable
        get():Boolean {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.favorite)
        }

    @SerializedName("ignored")
    @Bindable
    var name: String? = null
        get():String? {
            return field
        }
        set(name) {
            field = name
            notifyPropertyChanged(BR.name)
        }

    constructor() {}

    protected constructor(`in`: Parcel) {
        firstName = `in`.readString()
        lastName = `in`.readString()
        profilePicUrl = `in`.readString()
        mobile = `in`.readString()
        email = `in`.readString()
        favorite = `in`.readByte().toInt() != 0
    }

    val contentValues: ContentValues
        get() {
            val contentValues = ContentValues()
            contentValues.put(firstName, firstName)
            contentValues.put(lastName, lastName)
            contentValues.put(mobile, mobile)
            contentValues.put(email, email)
            contentValues.put(profilePicUrl, profilePicUrl)
            return contentValues
        }
}

