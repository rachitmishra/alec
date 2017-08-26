package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.user.UserProfileActivity
import android.view.View

class ContactListItemViewModel : Contact() {

    fun onViewContactClick(view: View) {
        UserProfileActivity.start(view.context, id)
    }
}
