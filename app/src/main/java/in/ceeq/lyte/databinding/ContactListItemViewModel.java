package in.ceeq.lyte.databinding;


import android.view.View;

import in.ceeq.lyte.ContactViewActivity;

public class ContactListItemViewModel extends Contact {

    public void onViewContactClick(View view) {
        ContactViewActivity.start(view.getContext(), getId());
    }
}

