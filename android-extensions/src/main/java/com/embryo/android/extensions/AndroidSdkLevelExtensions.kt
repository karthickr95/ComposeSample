@file:Suppress("unused")
@file:JvmName("SdkLevelExtension")

package com.embryo.android.extensions

import android.os.Build
import android.util.Log
import androidx.annotation.ChecksSdkIntAtLeast

/**
 *  Checks for is the Android version is equal and above satisfied.
 */

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
inline val isAndroid6AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
inline val isAndroid7AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
inline val isAndroid8AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
inline val isAndroid9AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
inline val isAndroid10AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
inline val isAndroid11AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
inline val isAndroid12AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
inline val isAndroid13AndAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

fun getAndroidVersionString(): String {
    return when {
        isAndroid13AndAbove -> "13"
        isAndroid12AndAbove -> "12"
        isAndroid11AndAbove -> "11"
        isAndroid10AndAbove -> "10"
        isAndroid9AndAbove -> "9"
        isAndroid8AndAbove -> "8"
        isAndroid7AndAbove -> "7"
        isAndroid6AndAbove -> "6"
        else -> "Unknown"
    }
}

fun printAndroidVersion(logTag: String) {
    val androidVersion: String = getAndroidVersionString()
    Log.d(logTag, "Android Version $androidVersion")
}
