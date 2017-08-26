package in.ceeq.lyte.user;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import in.ceeq.lyte.R;
import in.ceeq.lyte.databinding.ContactListItemViewModel;
import in.ceeq.lyte.databinding.ListItemContactBinding;
import in.ceeq.lyte.utils.CursorRecyclerViewAdapter;

public class UserListAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

    public UserListAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        ContactItemViewHolder contactItemViewHolder = ((ContactItemViewHolder) viewHolder);
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        ContactListItemViewModel contactListViewModel = new ContactListItemViewModel();
        contactItemViewHolder.getLayoutBinding().setContactListItemViewModel(contactListViewModel);
        contactItemViewHolder.getLayoutBinding().executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactListItemViewModel contactListViewModel = new ContactListItemViewModel();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemContactBinding listItemFeedbackBinding =
                DataBindingUtil.inflate(inflater, R.layout.list_item_contact, parent, false);
        listItemFeedbackBinding.setContactListItemViewModel(contactListViewModel);
        return new ContactItemViewHolder(listItemFeedbackBinding);
    }

    private static class ContactItemViewHolder extends RecyclerView.ViewHolder {

        private ListItemContactBinding mLayoutBinding;

        ContactItemViewHolder(ListItemContactBinding layoutBinding) {
            super(layoutBinding.getRoot());
            mLayoutBinding = layoutBinding;
        }

        public ListItemContactBinding getLayoutBinding() {
            return mLayoutBinding;
        }
    }
}

