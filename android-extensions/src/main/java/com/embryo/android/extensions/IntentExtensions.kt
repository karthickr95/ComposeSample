@file:JvmName("IntentExtensions")
@file:Suppress("unused")

package com.embryo.android.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import java.io.Serializable

@SuppressLint("NewApi")
inline fun <reified T : Parcelable> Intent.getCompatParcelableExtra(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
    }
}

@SuppressLint("NewApi")
inline fun <reified T : Parcelable> Intent.getCompatParcelableArrayListExtra(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): ArrayList<T>? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayListExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableArrayListExtra(key)
    }
}

@SuppressLint("NewApi")
inline fun <reified T : Serializable> Intent.getCompatSerializableExtra(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializableExtra(key) as? T
    }
}

/**
 * To use with Java
 */
@SuppressLint("NewApi")
fun <T : Parcelable> getCompatParcelableExtra(
    intent: Intent,
    key: String,
    clazz: Class<T>,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(key, clazz)
    } else {
        @Suppress("DEPRECATION")
        intent.getParcelableExtra(key)
    }
}

/**
 * To use with Java
 */
@SuppressLint("NewApi")
fun <T : Parcelable> getCompatParcelableArrayListExtra(
    intent: Intent,
    key: String,
    clazz: Class<T>,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): ArrayList<T>? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableArrayListExtra(key, clazz)
    } else {
        @Suppress("DEPRECATION")
        intent.getParcelableArrayListExtra(key)
    }
}

/**
 * To use with Java
 */
@SuppressLint("NewApi")
fun <T : Serializable> getCompatSerializableExtra(
    intent: Intent,
    key: String,
    clazz: Class<T>,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        intent.getSerializableExtra(key, clazz)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        intent.getSerializableExtra(key) as? T?
    }
}
