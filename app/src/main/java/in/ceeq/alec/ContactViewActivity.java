package in.ceeq.alec;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import in.ceeq.alec.databinding.ActivityContactViewBinding;
import in.ceeq.alec.databinding.BaseContactViewModel;
import in.ceeq.alec.databinding.ContactViewViewModel;


public class ContactViewActivity extends AppCompatActivity
        implements LoaderCallbacks<Cursor>, BaseContactViewModel.OnContactAddEditListener {

    private static final String EXTRA_CONTACT_ID = "extra_contact_id";
    private static final String CONTACT_VIEW_VIEW_MODEL = "contact_view_view_model";
    private ContactViewViewModel mContactViewViewModel;

    public static void start(Context context, int contactId) {
        Intent starter = new Intent(context, ContactViewActivity.class);
        starter.putExtra(EXTRA_CONTACT_ID, contactId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            mContactViewViewModel = savedInstanceState.getParcelable(CONTACT_VIEW_VIEW_MODEL);
        }

        if (null == mContactViewViewModel) {
            mContactViewViewModel = new ContactViewViewModel(this);
        } else {
            mContactViewViewModel.init(this);
        }

        ActivityContactViewBinding contactViewBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_contact_view);
        contactViewBinding.setContactViewViewModel(mContactViewViewModel);

        setSupportActionBar(contactViewBinding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);

        int contactId = getIntent().getIntExtra(EXTRA_CONTACT_ID, 0);
        mContactViewViewModel.setId(contactId);
        getLoaderManager().initLoader(0, null, this);
        mContactViewViewModel.getContact();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CONTACT_VIEW_VIEW_MODEL, mContactViewViewModel);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return mContactViewViewModel.load(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            mContactViewViewModel.loadFromCursor(cursor);
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_view, menu);
        MenuItem menuItem = menu.findItem(R.id.nav_favorite);
        Drawable favorite;
        if (mContactViewViewModel.getFavorite()) {
            favorite =
                    VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_star_white_24dp, null);
            menuItem.setIcon(favorite);
        } else {
            favorite =
                    VectorDrawableCompat.create(getResources(),
                            R.drawable.ic_star_border_white_24dp, null);
            menuItem.setIcon(favorite);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.nav_favorite:
                mContactViewViewModel.onFavoriteClick();
                return true;

            case R.id.nav_share:
                mContactViewViewModel.onShareClick(this);
                return true;

            case R.id.nav_edit:
                mContactViewViewModel.onEditClick(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onContactAddEditSuccess() {

    }
}

