package `in`.ceeq.lyte.utils.databinding

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open abstract class BaseRecyclerBindingAdapter<T>(private val mItems: ObservableArrayList<T> = ObservableArrayList())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val obj1 = object : ObservableList.OnListChangedCallback<ObservableArrayList<T>>() {
        override fun onItemRangeRemoved(p0: ObservableArrayList<T>, p1: Int, p2: Int) {
            this@BaseRecyclerBindingAdapter.notifyItemRangeRemoved(p1, p2)
        }

        override fun onChanged(p0: ObservableArrayList<T>) {
            this@BaseRecyclerBindingAdapter.notifyDataSetChanged()
        }

        override fun onItemRangeMoved(p0: ObservableArrayList<T>, p1: Int, p2: Int, p3: Int) {
            this@BaseRecyclerBindingAdapter.notifyItemRangeChanged(p1, p3)
        }

        override fun onItemRangeChanged(p0: ObservableArrayList<T>, p1: Int, p2: Int) {
            this@BaseRecyclerBindingAdapter.notifyItemRangeChanged(p1, p2)
        }

        override fun onItemRangeInserted(p0: ObservableArrayList<T>, p1: Int, p2: Int) {
            this@BaseRecyclerBindingAdapter.notifyItemRangeInserted(p1, p2)
        }

    }

    init {
        mItems.addOnListChangedCallback(obj1)
    }

    fun setItems(items: ObservableArrayList<T>) {
        if (mItems == items) {
            return
        }

        mItems.clear()
        mItems.addOnListChangedCallback(obj1)
        mItems.addAll(items)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        mItems.removeOnListChangedCallback(obj1)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = bindView(holder, position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent, viewType)

    override fun getItemCount() = mItems.size

    abstract fun bindView(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
}
