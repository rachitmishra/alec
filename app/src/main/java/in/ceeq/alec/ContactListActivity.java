package in.ceeq.alec;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import in.ceeq.alec.databinding.ActivityContactListBinding;
import in.ceeq.alec.databinding.ContactListViewModel;

public class ContactListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ContactListViewModel mContactListViewModel;
    private ContactListAdapter mContactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactListViewModel = new ContactListViewModel(this);

        ActivityContactListBinding contactEditBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_contact_list);
        contactEditBinding.setContactListViewModel(mContactListViewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        mContactListAdapter = new ContactListAdapter(null);

        RecyclerView recyclerView = contactEditBinding.layoutList.recyclerView;
        recyclerView.setHasFixedSize(Boolean.TRUE);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mContactListAdapter);

        getLoaderManager().initLoader(0, null, this);

        mContactListViewModel.getAllContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mContactListViewModel.loadAll(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mContactListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
