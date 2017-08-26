package `in`.ceeq.lyte.utils.kotlin_ext

import android.content.Context

fun Context.getLocationService() = this.getSystemService(Context.LOCATION_SERVICE)
