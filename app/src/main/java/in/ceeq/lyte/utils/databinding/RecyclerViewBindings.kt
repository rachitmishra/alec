package `in`.ceeq.lyte.utils.databinding

import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView

object RecyclerViewBindings {

    @BindingAdapter("items")
    @JvmStatic fun <T> setItems(recyclerView: RecyclerView, items: ObservableArrayList<T>) {
        @Suppress("UNCHECKED_CAST")
        val adapter = recyclerView.adapter as BaseRecyclerBindingAdapter<T>
        adapter.setItems(items)
    }
}
