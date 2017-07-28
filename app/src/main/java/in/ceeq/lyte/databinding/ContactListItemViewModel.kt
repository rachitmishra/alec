package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.ContactViewActivity
import android.view.View

class ContactListItemViewModel : Contact() {

    fun onViewContactClick(view: View) {
        ContactViewActivity.start(view.context, id)
    }
}
