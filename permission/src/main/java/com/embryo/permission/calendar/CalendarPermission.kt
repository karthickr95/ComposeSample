package com.embryo.permission.calendar

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import com.embryo.permission.common.extensions.checkPermissionGranted

fun Context.isReadCalendarPermissionGranted(): Boolean =
    checkPermissionGranted(Manifest.permission.READ_CALENDAR)

fun Context.isWriteCalendarPermissionGranted(): Boolean =
    checkPermissionGranted(Manifest.permission.WRITE_CALENDAR)

fun Context.isAllCalendarPermissionGranted(): Boolean =
    checkPermissionGranted(Manifest.permission.READ_CALENDAR) &&
        checkPermissionGranted(Manifest.permission.WRITE_CALENDAR)

fun ActivityResultLauncher<Array<String>>.launchForAllCalendarPermission() {
    launch(
        arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
        ),
    )
}
