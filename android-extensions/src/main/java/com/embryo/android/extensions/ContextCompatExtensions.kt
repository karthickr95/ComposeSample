@file:Suppress("unused")

package com.embryo.android.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@Suppress("NOTHING_TO_INLINE")
@ColorInt
inline fun Context.getCompatColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getCompatColorStateList(@ColorRes id: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, id)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}
