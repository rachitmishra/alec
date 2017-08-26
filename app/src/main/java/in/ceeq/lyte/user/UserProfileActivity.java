package in.ceeq.lyte.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import in.ceeq.lyte.R;
import in.ceeq.lyte.databinding.ActivityContactViewBinding;
import in.ceeq.lyte.databinding.ContactViewModel;


public class UserProfileActivity extends AppCompatActivity {

    private static final String EXTRA_CONTACT_ID = "extra_contact_id";
    private static final String CONTACT_VIEW_VIEW_MODEL = "contact_view_view_model";
    private ContactViewModel mContactViewModel;

    public static void start(Context context, int contactId) {
        Intent starter = new Intent(context, UserProfileActivity.class);
        starter.putExtra(EXTRA_CONTACT_ID, contactId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            mContactViewModel = savedInstanceState.getParcelable(CONTACT_VIEW_VIEW_MODEL);
        }

        if (null == mContactViewModel) {
            mContactViewModel = new ContactViewModel(this);
        } else {
            mContactViewModel.init(this);
        }

        ActivityContactViewBinding contactViewBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_contact_view);
        contactViewBinding.setContactViewModel(mContactViewModel);

        setSupportActionBar(contactViewBinding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);

        int contactId = getIntent().getIntExtra(EXTRA_CONTACT_ID, 0);
        mContactViewModel.setId(contactId);
        mContactViewModel.getContact();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable(CONTACT_VIEW_VIEW_MODEL, mContactViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_view, menu);
        MenuItem menuItem = menu.findItem(R.id.nav_favorite);
        Drawable favorite;
        if (mContactViewModel.getFavorite()) {
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
                mContactViewModel.onFavoriteClick();
                return true;

            case R.id.nav_share:
                mContactViewModel.onShareClick(this);
                return true;

            case R.id.nav_edit:
                mContactViewModel.onEditClick(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

