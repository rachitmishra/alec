package `in`.ceeq.lyte.messages


import `in`.ceeq.lyte.data.Message
import `in`.ceeq.lyte.data.MessageText
import `in`.ceeq.lyte.firebase.FirebaseAuthHelper
import `in`.ceeq.lyte.firebase.FirebaseDatabaseHelper
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    var messageText = ObservableField<MessageText>(MessageText())
    var messageList = ObservableArrayList<Message>()
    private val messageDatabaseHelper by lazy { FirebaseDatabaseHelper<Message>() }
    private val loggedIn = FirebaseAuthHelper

    fun isLoggedIn() = loggedIn

    fun sendMessage() {
        val message = Message(getUser(), getPhotoUrl(), messageText.get()?.text ?: "")
        this@MessageViewModel.messageText.set(MessageText())
        messageDatabaseHelper.push(message)
        messageText.set(MessageText(""))
    }

    fun attachImage() {
    }

    private fun getUser() = "Rachit"

    private fun getPhotoUrl() = ""

}

