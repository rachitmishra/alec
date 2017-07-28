package `in`.ceeq.lyte.databinding


import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.content.OperationApplicationException
import android.database.Cursor
import android.databinding.Bindable
import android.os.Parcel
import android.os.RemoteException
import android.util.Log

import java.util.ArrayList

import `in`.ceeq.lyte.BR
import `in`.ceeq.lyte.BuildConfig
import `in`.ceeq.lyte.network.ContactService
import `in`.ceeq.lyte.provider.contentprovider.ContactContract
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import `in`.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.SERVER_ID
import android.os.Parcelable

open class BaseContactViewModel : Contact {

    @get:Bindable
    var progressViewVisible: Boolean = false
        set(progressViewVisible) {
            field = progressViewVisible
            notifyPropertyChanged(BR.progressViewVisible)
        }
    private var mRetrofit: Retrofit? = null
    private var mContentResolver: ContentResolver? = null


    constructor(context: Context) {
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()
        mContentResolver = context.contentResolver
    }

    fun init(context: Context) {
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()
        mContentResolver = context.contentResolver
    }

    fun load(context: Context): Loader<Cursor> {
        val selection = SERVER_ID + "= ?"
        val selectionArgs = arrayOf(id.toString())
        return CursorLoader(context, ContactContract.Contacts.contentUri,
                ContactContract.Contacts.defaultProjection, selection, selectionArgs, null)
    }

    fun loadAll(context: Context): Loader<Cursor> {
        return CursorLoader(context, ContactContract.Contacts.contentUri,
                ContactContract.Contacts.defaultProjection, null, null,
                ContactContract.Contacts.firstName + " ASC")
    }

    private val contactService: ContactService
        get() = mRetrofit!!.create(ContactService::class.java)

    fun getAllContacts() {
        progressViewVisible = true
        val contactViewModelCall = contactService.contacts
        contactViewModelCall.enqueue(object : Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>,
                                    response: Response<List<Contact>>) {
                val contacts = response.body()
                bulkInsert(contacts)
                progressViewVisible = false
            }

            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {

            }
        })
    }

    fun getContact() {
        progressViewVisible = true
        val contactGetCall = contactService.getContact(id)
        contactGetCall.enqueue(object : Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (null != response.body()) {
                    update(response.body())
                    progressViewVisible = false
                }
            }

            override fun onFailure(call: Call<Contact>, t: Throwable) {
                progressViewVisible = false
            }
        })
    }

    fun putContact(onContactAddEditListener: OnContactAddEditListener) {
        progressViewVisible = true
        val contactUpdateCall = contactService.putContact(this, id)
        contactUpdateCall.enqueue(object : Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (null != response.body()) {
                    update(response.body())
                    progressViewVisible = false
                    onContactAddEditListener.onContactAddEditSuccess()
                }
            }

            override fun onFailure(call: Call<Contact>, t: Throwable) {
                progressViewVisible = false
            }
        })
    }

    fun postContact(onContactAddEditListener: OnContactAddEditListener) {
        progressViewVisible = true
        val contactUpdateCall = contactService.postContact(this)
        contactUpdateCall.enqueue(object : Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (null != response.body()) {
                    insert(response.body())
                    progressViewVisible = false
                    onContactAddEditListener.onContactAddEditSuccess()
                }
            }

            override fun onFailure(call: Call<Contact>, t: Throwable) {
                Log.w(BuildConfig.APPLICATION_ID, t.cause)
                progressViewVisible = false
            }
        })
    }


    protected fun update(contact: Contact) {
        val selectionArgs = arrayOf(contact.id.toString())
        val contactContentValues = contact.contentValues
        contactContentValues.remove(serverId)
        mContentResolver!!.update(ContactContract.Contacts.contentUri,
                contactContentValues, SERVER_ID + "= ?", selectionArgs)
    }

    private fun insert(contact: Contact) {
        mContentResolver!!.insert(ContactContract.Contacts.contentUri,
                contact.contentValues)
    }

    private fun bulkInsert(contacts: List<Contact>) {
        val operationList = ArrayList<ContentProviderOperation>()
        if (!contacts.isEmpty()) {
            for (contact in contacts) {
                val contactContentValues = contact.contentValues
                val contentProviderOperation = ContentProviderOperation
                        .newInsert(ContactContract.Contacts.contentUri)
                        .withValues(contactContentValues)
                        .build()
                operationList.add(contentProviderOperation)
            }
        }

        try {
            mContentResolver!!.applyBatch(ContactContract.AUTHORITY, operationList)
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: OperationApplicationException) {
            e.printStackTrace()
        }

    }

    interface OnContactAddEditListener {
        fun onContactAddEditSuccess()
    }

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        private val BASE_URL = "https://jsonplaceholder.typicode.com/"

        val CREATOR: Parcelable.Creator<BaseContactViewModel> = object : Parcelable.Creator<BaseContactViewModel> {
            override fun createFromParcel(source: Parcel): BaseContactViewModel {
                return BaseContactViewModel(source)
            }

            override fun newArray(size: Int): Array<BaseContactViewModel> {
                return arrayOfNulls(size)
            }
        }
    }
}

