@file:Suppress("unused")

package com.embryo.android.extensions

import android.os.Handler
import android.view.View

inline fun View.postDelayedSafely(delayInMillis: Long, crossinline action: (() -> Unit)) {
    postDelayed(
        {
            try {
                action()
            } catch (e: Exception) {
                // Do nothing
            }
        },
        delayInMillis,
    )
}

inline fun View.postSafely(crossinline action: () -> Unit) {
    post {
        try {
            action()
        } catch (e: Exception) {
            // Do nothing
        }
    }
}

inline fun Handler.postDelayedSafely(delayInMillis: Long, crossinline action: (() -> Unit)) {
    postDelayed(
        {
            try {
                action()
            } catch (e: Exception) {
                // Do nothing
            }
        },
        delayInMillis,
    )
}
