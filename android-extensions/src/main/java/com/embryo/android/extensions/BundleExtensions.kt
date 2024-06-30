@file:JvmName("BundleExtensions")

package com.embryo.android.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import java.io.Serializable

@SuppressLint("NewApi")
inline fun <reified T : Parcelable> Bundle.getCompatParcelable(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key)
    }
}

@SuppressLint("NewApi")
inline fun <reified T : Parcelable> Bundle.getCompatParcelableArrayList(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): ArrayList<T>? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayList(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableArrayList(key)
    }
}

@SuppressLint("NewApi")
inline fun <reified T : Parcelable> Bundle.getCompatParcelableArray(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): Array<T>? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArray(key, T::class.java)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        getParcelableArray(key) as? Array<T>?
    }
}

@SuppressLint("NewApi")
inline fun <reified T : Serializable> Bundle.getCompatSerializable(
    key: String,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializable(key) as? T
    }
}

/**
 * To use with Java
 */
@SuppressLint("NewApi")
fun <T : Parcelable> getCompatParcelable(
    bundle: Bundle,
    key: String,
    clazz: Class<T>,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        bundle.getParcelable(key, clazz)
    } else {
        @Suppress("DEPRECATION")
        bundle.getParcelable(key)
    }
}

/**
 * To use with Java
 */
@SuppressLint("NewApi")
fun <T : Parcelable> getCompatParcelableArrayList(
    bundle: Bundle,
    key: String,
    clazz: Class<T>,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): ArrayList<T>? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        bundle.getParcelableArrayList(key, clazz)
    } else {
        @Suppress("DEPRECATION")
        bundle.getParcelableArrayList(key)
    }
}

/**
 * To use with Java
 */
@SuppressLint("NewApi")
fun <T : Serializable> getCompatSerializable(
    bundle: Bundle,
    key: String,
    clazz: Class<T>,
    @VisibleForTesting version: Int = Build.VERSION.SDK_INT,
): T? {
    return if (version >= Build.VERSION_CODES.TIRAMISU) {
        bundle.getSerializable(key, clazz)
    } else {
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        bundle.getSerializable(key) as? T?
    }
}
