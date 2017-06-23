package in.ceeq.lyte.databinding;


import android.content.Context;
import android.view.View;

import in.ceeq.lyte.ContactEditActivity;

public class ContactListViewModel extends BaseContactViewModel {

    public ContactListViewModel(Context context) {
        super(context);
    }

    public void onAddContactClick(View view) {
        ContactEditActivity.start(view.getContext());
    }
}

