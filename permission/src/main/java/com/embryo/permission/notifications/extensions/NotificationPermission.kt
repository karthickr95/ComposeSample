@file:JvmName("NotificationPermissionExtension")

package com.embryo.permission.notifications.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.app.NotificationManagerCompat
import com.embryo.android.extensions.isAndroid13AndAbove
import com.embryo.permission.common.extensions.checkPermissionGranted


internal fun Context.areNotificationsEnabled(): Boolean =
    NotificationManagerCompat.from(this).areNotificationsEnabled()

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun Context.isNotificationPermissionGranted(): Boolean =
    if (isAndroid13AndAbove) {
        checkPermissionGranted(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        areNotificationsEnabled()
    }

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun Context.isNotificationPermissionNotGranted(): Boolean =
    isNotificationPermissionGranted().not()

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun Activity.shouldShowNotificationPermissionRationale(): Boolean =
    if (isAndroid13AndAbove) {
        shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        !areNotificationsEnabled()
    }

/*@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun ActivityResultLauncher<String>.launchForNotificationPermission() {
    if (isAndroid13AndAbove) {
        launch(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        BaseHealthifyMeUtils.startNotificationDetailsActivity(BaseApplication.baseInstance)
    }
}*/
