@file:JvmName("AlarmPermissionExtension")

package com.embryo.permission.alarm.extensions

import android.app.AlarmManager
import android.content.Context
import android.os.Build

fun Context.isAlarmPermissionGranted(): Boolean = canScheduleExactAlarms(this)

fun canScheduleExactAlarms(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    return canScheduleExactAlarms(alarmManager)
}

fun canScheduleExactAlarms(alarmManager: AlarmManager): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        alarmManager.canScheduleExactAlarms()
    } else {
        // Older devices can schedule alarms without any permission.
        true
    }
}
