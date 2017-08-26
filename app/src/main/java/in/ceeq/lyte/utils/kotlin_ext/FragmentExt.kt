package `in`.ceeq.lyte.utils.kotlin_ext

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.ViewGroup

fun inflate(layoutId: Int, container: ViewGroup?): ViewDataBinding {
    return DataBindingUtil.inflate(container?.getLayoutInflater(), layoutId, container, false)
}
