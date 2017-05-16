package in.ceeq.alec;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.ceeq.alec.databinding.ActivityContactViewBinding;
import in.ceeq.alec.databinding.ContactViewViewModel;


public class LoginActivity extends AppCompatActivity {

    private static final String EXTRA_CONTACT_ID = "extra_contact_id";
    private static final String CONTACT_VIEW_VIEW_MODEL = "contact_view_view_model";
    private ContactViewViewModel mContactViewViewModel;

    public static void start(Context context, int contactId) {
        Intent starter = new Intent(context, LoginActivity.class);
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
        mContactViewViewModel.getContact();
    }
}

