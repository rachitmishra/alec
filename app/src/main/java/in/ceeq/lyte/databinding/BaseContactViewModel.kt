package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.BR
import `in`.ceeq.lyte.BuildConfig
import `in`.ceeq.lyte.data.ContactService
import android.content.ContentResolver
import android.content.Context
import android.databinding.Bindable
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseContactViewModel : Contact {

    @Bindable
    var progressViewVisible: Boolean = false
        get(): Boolean{
            return field
        }
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressViewVisible)
        }

    private var mRetrofit: Retrofit? = null
    private var mContentResolver: ContentResolver? = null


    constructor(context: Context) {
        mRetrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()
        mContentResolver = context.contentResolver
    }

    fun init(context: Context) {
        mRetrofit = Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()
        mContentResolver = context.contentResolver
    }

    private val contactService: ContactService
        get() = mRetrofit!!.create(ContactService::class.java)

    fun getAllContacts() {
        progressViewVisible = true
        val contactViewModelCall = contactService.contacts
        contactViewModelCall.enqueue(object : Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>,
                                    response: Response<List<Contact>>) {
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

    interface OnContactAddEditListener {
        fun onContactAddEditSuccess()
    }
}
