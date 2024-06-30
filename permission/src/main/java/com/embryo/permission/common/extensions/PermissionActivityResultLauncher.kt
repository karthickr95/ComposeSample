@file:Suppress("unused")
@file:JvmName("PermissionActivityResultLauncher")

package com.embryo.permission.common.extensions

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


fun ComponentActivity.registerForPermissionResult(callback: ActivityResultCallback<Boolean>): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)
}

fun ComponentActivity.registerForMultiplePermissionResult(callback: ActivityResultCallback<Map<String, Boolean>>): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)
}

/*fun Fragment.registerForPermissionResult(callback: ActivityResultCallback<Boolean>): ActivityResultLauncher<String> {
    return registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)
}*/
