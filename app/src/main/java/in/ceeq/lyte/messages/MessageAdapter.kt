package `in`.ceeq.lyte.messages

import `in`.ceeq.lyte.data.Message
import `in`.ceeq.lyte.databinding.ListItemMessageBinding
import `in`.ceeq.lyte.utils.databinding.BaseRecyclerBindingAdapter
import `in`.ceeq.lyte.utils.kotlin_ext.getLayoutInflater
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MessageAdapter(private val mItems: ObservableArrayList<Message> = ObservableArrayList())
    : BaseRecyclerBindingAdapter<Message>(mItems) {

    override fun bindView(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mItems[position]
        when (holder) {
            is ViewHolder1 -> {
                val messageViewModel = MessageItemViewModel.from(item)
                holder.bind(messageViewModel)
            }
        }
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemMessageBinding.inflate(parent.getLayoutInflater())
        return ViewHolder1(binding)
    }

    override fun getItemCount() = mItems.size

    inner class ViewHolder1(private val viewBinding: ListItemMessageBinding)
        : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(messageViewModel: MessageItemViewModel) {
            viewBinding.message = messageViewModel
            viewBinding.executePendingBindings()
        }
    }
}
