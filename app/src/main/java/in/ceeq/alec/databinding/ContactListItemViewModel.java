package in.ceeq.alec.databinding;


import android.view.View;

import in.ceeq.alec.ContactViewActivity;

public class ContactListItemViewModel extends Contact {

    public void onViewContactClick(View view) {
        ContactViewActivity.start(view.getContext(), getId());
    }
}

