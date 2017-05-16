package in.ceeq.alec;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.net.MalformedURLException;

import in.ceeq.alec.databinding.ActivityContactEditBinding;
import in.ceeq.alec.databinding.BaseContactViewModel;
import in.ceeq.alec.databinding.ContactEditViewModel;
import in.ceeq.alec.utils.LogUtils;
import in.ceeq.alec.utils.SoftInputUtils;

public class ContactEditActivity extends AppCompatActivity
        implements LoaderCallbacks<Cursor>, BaseContactViewModel.OnContactAddEditListener {

    private static final String ACTION_ADD = BuildConfig.APPLICATION_ID + ".action.ADD";

    private static final String EXTRA_CONTACT_ID = "extra_contact_id";
    private static final String CONTACT_EDIT_VIEW_MODEL = "contact_edit_view_model";
    private ContactEditViewModel mContactEditViewModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, ContactEditActivity.class);
        starter.setAction(ACTION_ADD);
        context.startActivity(starter);
    }

    public static void start(Context context, int contactId) {
        Intent starter = new Intent(context, ContactEditActivity.class);
        starter.putExtra(EXTRA_CONTACT_ID, contactId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            mContactEditViewModel = savedInstanceState.getParcelable(CONTACT_EDIT_VIEW_MODEL);
        }

        if (null == mContactEditViewModel) {
            mContactEditViewModel = new ContactEditViewModel(this);
        } else {
            mContactEditViewModel.init(this);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Contact");

        ActivityContactEditBinding contactEditBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_contact_edit);
        contactEditBinding.setContactEditViewModel(mContactEditViewModel);

        String mAction = getIntent().getAction();

        if (!ACTION_ADD.equalsIgnoreCase(mAction)) {
            int contactId = getIntent().getIntExtra(EXTRA_CONTACT_ID, 0);
            mContactEditViewModel.setId(contactId);
            getLoaderManager().initLoader(0, null, this);
            getSupportActionBar().setTitle("Edit Contact");
        } else {
            SoftInputUtils.showKeyboard(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.nav_save:
                mContactEditViewModel.onSaveClick();
                SoftInputUtils.hideKeyboard(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CONTACT_EDIT_VIEW_MODEL, mContactEditViewModel);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return mContactEditViewModel.load(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            mContactEditViewModel.loadFromCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
                mContactEditViewModel.handlePhotoSelection(data);
            } catch (MalformedURLException e) {
                LogUtils.LOG(e);
            }
        }
    }

    @Override
    public void onContactAddEditSuccess() {
        finish();
    }
}

