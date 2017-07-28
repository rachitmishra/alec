package `in`.ceeq.lyte

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup

fun <T> DataBindingUtil.inflate(layoutInflater: LayoutInflater,
                                layoutId: Int, container: ViewGroup): ViewDataBinding {
    return DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
}
