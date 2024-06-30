@file:JvmName("ActivityExtensions")

package com.embryo.android.extensions

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController

fun Activity.setLightStatusBars() {
    val rootView: View = window.decorView.findViewById(android.R.id.content)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        rootView.windowInsetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        rootView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
