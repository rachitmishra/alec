package `in`.ceeq.lyte.databinding


import `in`.ceeq.lyte.user.UserEditActivity
import android.content.Context
import android.view.View

class ContactListViewModel(context: Context) : BaseContactViewModel(context) {

    fun onAddContactClick(view: View) {
        UserEditActivity.start(view.context)
    }
}
