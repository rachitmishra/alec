package `in`.ceeq.lyte.messages

import `in`.ceeq.lyte.data.Message
import android.databinding.ObservableField

class MessageItemViewModel private constructor(message: Message) {

    val message = ObservableField<Message>(Message())

    companion object {
        fun from(message: Message) = MessageItemViewModel(message)
    }

    init {
        this.message.set(message)
    }
}
