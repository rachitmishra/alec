package `in`.ceeq.lyte.utils.kotlin_ext

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

fun <T : ViewDataBinding> Activity.bindContentView(layoutId: Int): T
        = DataBindingUtil.setContentView<T>(this, layoutId)
