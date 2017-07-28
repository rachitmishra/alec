package `in`.ceeq.lyte.gallery

import `in`.ceeq.lyte.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class ItemAdapter(private val mItems: List<Item>, private val mListener: ItemListener?) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setData(mItems[position])

    override fun getItemCount() = mItems.size

    interface ItemListener {
        fun onItemClick(itemPosition: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var imageView: ImageView
        var textView: TextView
        lateinit var item: Item

        init {
            itemView.setOnClickListener(this)
            imageView = itemView.findViewById<View>(R.id.imageView) as ImageView
            textView = itemView.findViewById<View>(R.id.textView) as TextView
        }

        fun setData(item: Item) {
            this.item = item
            val drawableRes = item.drawableResource
            if (drawableRes > 0) {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(drawableRes)
            } else {
                imageView.visibility = View.GONE
            }
            textView.text = item.title
        }

        override fun onClick(v: View) {
            mListener?.onItemClick(layoutPosition)
        }
    }
}
