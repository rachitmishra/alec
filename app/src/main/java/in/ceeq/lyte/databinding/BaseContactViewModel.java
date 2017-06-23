package in.ceeq.lyte.databinding;


import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.ceeq.lyte.BR;
import in.ceeq.lyte.BuildConfig;
import in.ceeq.lyte.network.ContactService;
import in.ceeq.lyte.provider.contentprovider.ContactContract;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static in.ceeq.lyte.provider.contentprovider.ContactContract.Contacts.SERVER_ID;

public class BaseContactViewModel extends Contact {

    private boolean progressViewVisible;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private Retrofit mRetrofit;
    private ContentResolver mContentResolver;


    public BaseContactViewModel(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        mContentResolver = context.getContentResolver();
    }

    public void init(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        mContentResolver = context.getContentResolver();
    }

    public Loader<Cursor> load(Context context) {
        String selection = SERVER_ID + "= ?";
        String[] selectionArgs = {String.valueOf(getId())};
        return new CursorLoader(context, ContactContract.Contacts.CONTENT_URI,
                ContactContract.Contacts.DEFAULT_PROJECTION, selection, selectionArgs, null);
    }

    public Loader<Cursor> loadAll(Context context) {
        return new CursorLoader(context, ContactContract.Contacts.CONTENT_URI,
                ContactContract.Contacts.DEFAULT_PROJECTION, null, null,
                ContactContract.Contacts.FIRST_NAME + " ASC");
    }

    private ContactService getContactService() {
        return mRetrofit.create(ContactService.class);
    }

    public void getAllContacts() {
        setProgressViewVisible(true);
        Call<List<Contact>> contactViewModelCall =
                getContactService().getContacts();
        contactViewModelCall.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call,
                                   Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                bulkInsert(contacts);
                setProgressViewVisible(false);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }
        });
    }

    public void getContact() {
        setProgressViewVisible(true);
        Call<Contact> contactGetCall = getContactService().getContact(getId());
        contactGetCall.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (null != response.body()) {
                    update(response.body());
                    setProgressViewVisible(false);
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                setProgressViewVisible(false);
            }
        });
    }

    public void putContact(final OnContactAddEditListener onContactAddEditListener) {
        setProgressViewVisible(true);
        Call<Contact> contactUpdateCall = getContactService().putContact(this, getId());
        contactUpdateCall.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (null != response.body()) {
                    update(response.body());
                    setProgressViewVisible(false);
                    onContactAddEditListener.onContactAddEditSuccess();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                setProgressViewVisible(false);
            }
        });
    }

    public void postContact(final OnContactAddEditListener onContactAddEditListener) {
        setProgressViewVisible(true);
        Call<Contact> contactUpdateCall = getContactService().postContact(this);
        contactUpdateCall.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (null != response.body()) {
                    insert(response.body());
                    setProgressViewVisible(false);
                    onContactAddEditListener.onContactAddEditSuccess();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.w(BuildConfig.APPLICATION_ID, t.getCause());
                setProgressViewVisible(false);
            }
        });
    }


    protected void update(Contact contact) {
        String[] selectionArgs = {String.valueOf(contact.getId())};
        ContentValues contactContentValues = contact.getContentValues();
        contactContentValues.remove(SERVER_ID);
        mContentResolver.update(ContactContract.Contacts.CONTENT_URI,
                contactContentValues, SERVER_ID + "= ?", selectionArgs);
    }

    private void insert(Contact contact) {
        mContentResolver.insert(ContactContract.Contacts.CONTENT_URI,
                contact.getContentValues());
    }

    private void bulkInsert(List<Contact> contacts) {
        ArrayList<ContentProviderOperation> operationList = new ArrayList<>();
        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                ContentValues contactContentValues = contact.getContentValues();
                ContentProviderOperation contentProviderOperation = ContentProviderOperation
                        .newInsert(ContactContract.Contacts.CONTENT_URI)
                        .withValues(contactContentValues)
                        .build();
                operationList.add(contentProviderOperation);
            }
        }

        try {
            mContentResolver.applyBatch(ContactContract.AUTHORITY, operationList);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    public void setProgressViewVisible(boolean progressViewVisible) {
        this.progressViewVisible = progressViewVisible;
        notifyPropertyChanged(BR.progressViewVisible);
    }

    @Bindable
    public boolean getProgressViewVisible() {
        return progressViewVisible;
    }

    public interface OnContactAddEditListener {
        void onContactAddEditSuccess();
    }

    public static final Creator<BaseContactViewModel> CREATOR = new Creator<BaseContactViewModel>() {
        @Override
        public BaseContactViewModel createFromParcel(Parcel source) {
            return new BaseContactViewModel(source);
        }

        @Override
        public BaseContactViewModel[] newArray(int size) {
            return new BaseContactViewModel[size];
        }
    };

    protected BaseContactViewModel(Parcel in) {
        super(in);
    }
}

