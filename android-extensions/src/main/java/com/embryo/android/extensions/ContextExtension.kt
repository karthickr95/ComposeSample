@file:Suppress("unused")

package com.embryo.android.extensions

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.*

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getAlarmManager(): AlarmManager =
    this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getColorStateListOf(@ColorRes id: Int): ColorStateList {
    return ColorStateList.valueOf(ContextCompat.getColor(this, id))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getColorStateListOfColor(@ColorInt color: Int): ColorStateList {
    return ColorStateList.valueOf(color)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getDimensionPixelSize(@DimenRes dimenResId: Int): Int {
    return resources.getDimensionPixelSize(dimenResId)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getDimension(@DimenRes dimenResId: Int): Float {
    return resources.getDimension(dimenResId)
}

@RequiresApi(Build.VERSION_CODES.R)
fun Context.historicalExitReasonsByProcessName(
    name: String,
    n: Int = 10,
): List<String> {
    val activityManager = applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager

    return activityManager.getHistoricalProcessExitReasons(null, 0, 0)
        .filter { it.processName == name }
        .take(n)
        .map { "${Date(it.timestamp)} - Reason: ${it.reason}: ${it.description}" }
}

/**
 * If your app is registering a receiver only for system broadcasts through
 * Context#registerReceiver methods, such as Context#registerReceiver(), then it shouldn't
 * specify a flag when registering the receiver.
 *
 * https://developer.android.com/about/versions/14/behavior-changes-14#system-broadcasts
 */
@SuppressLint("UnspecifiedRegisterReceiverFlag")
fun Context.registerReceiverSystemOnly(receiver: BroadcastReceiver, filter: IntentFilter) {
    try {
        this.registerReceiver(receiver, filter)
    } catch (e: Exception) {
        // Do nothing
    }
}

/**
 * Use this if listening only broadcasts from the system or your own app.
 */
fun Context.registerReceiverNotExported(receiver: BroadcastReceiver, filter: IntentFilter) {
    try {
        ContextCompat.registerReceiver(this, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
    } catch (e: Exception) {
        onReceiverException(this, receiver, filter)
    }
}

/**
 * Use this if listening for broadcasts sent from other apps, even other apps that you own.
 * Other app includes GMS, Play Store, etc.
 */
fun Context.registerReceiverExported(receiver: BroadcastReceiver, filter: IntentFilter) {
    try {
        ContextCompat.registerReceiver(this, receiver, filter, ContextCompat.RECEIVER_EXPORTED)
    } catch (e: Exception) {
        onReceiverException(this, receiver, filter)
    }
}

/**
 * ASOP Issue: https://issuetracker.google.com/issues/291834495
 */
private fun onReceiverException(
    context: Context,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
) {
    try {
        context.registerReceiver(receiver, filter)
    } catch (e: Exception) {
        // Do nothing
    }
}
