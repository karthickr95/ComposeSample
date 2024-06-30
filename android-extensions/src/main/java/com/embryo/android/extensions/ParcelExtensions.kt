@file:JvmName("ParcelExtensions")

package com.embryo.android.extensions

import android.os.Build
import android.os.Parcel
import android.os.Parcelable

inline fun <reified T : Parcelable> Parcel.readCompatParcelable(classLoader: ClassLoader?): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        readParcelable(classLoader, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        readParcelable(classLoader)
    }
}
