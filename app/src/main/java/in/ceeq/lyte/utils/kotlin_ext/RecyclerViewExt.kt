package `in`.ceeq.lyte.utils.kotlin_ext

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

infix fun <T : RecyclerView.Adapter<out RecyclerView.ViewHolder>> RecyclerView.initVertical(adapter: T) {
    this.layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}

fun inflate(layoutId: Int, parent: ViewGroup, attachToParent: Boolean = false): View =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, attachToParent)

