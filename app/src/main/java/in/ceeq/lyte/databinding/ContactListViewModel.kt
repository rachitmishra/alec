package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.ContactEditActivity
import android.content.Context
import android.view.View

class ContactListViewModel(context: Context) : BaseContactViewModel(context) {

    fun onAddContactClick(view: View) {
        ContactEditActivity.start(view.context)
    }
}
