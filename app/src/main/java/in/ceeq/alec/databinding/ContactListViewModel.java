package in.ceeq.alec.databinding;


import android.content.Context;
import android.view.View;

import in.ceeq.alec.ContactEditActivity;

public class ContactListViewModel extends BaseContactViewModel {

    public ContactListViewModel(Context context) {
        super(context);
    }

    public void onAddContactClick(View view) {
        ContactEditActivity.start(view.getContext());
    }
}

