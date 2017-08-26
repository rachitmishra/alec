package `in`.ceeq.lyte.messages

import `in`.ceeq.lyte.BuildConfig
import `in`.ceeq.lyte.R
import `in`.ceeq.lyte.databinding.ActivityMessagesBinding
import `in`.ceeq.lyte.login.LoginActivity
import `in`.ceeq.lyte.utils.SoftInputUtils
import `in`.ceeq.lyte.utils.kotlin_ext.initVertical
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MessagesActivity : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProviders.of(this)
                .get(MessageViewModel::class.java)
                .isLoggedIn()
                .observe(this, )

        val activityMessageBinding =
                DataBindingUtil.setContentView<ActivityMessagesBinding>(this, R.layout.activity_messages)
        activityMessageBinding.messageViewModel = mMessageViewModel

        // init RecyclerView
        activityMessageBinding.recyclerViewMessages initVertical MessageAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_contact_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.nav_save -> {
                SoftInputUtils.hideKeyboard(this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    companion object {

        private val ACTION_ADD = BuildConfig.APPLICATION_ID + ".action.ADD"

        private val EXTRA_CONVERSATION_ID = "extra_conversation_id"

        fun start(context: Context) {
            val starter = Intent(context, MessagesActivity::class.java)
            starter.action = ACTION_ADD
            context.startActivity(starter)
        }

        fun start(context: Context, conversationId: Int) {
            val starter = Intent(context, MessagesActivity::class.java)
            starter.putExtra(EXTRA_CONVERSATION_ID, conversationId)
            context.startActivity(starter)
        }
    }
}

