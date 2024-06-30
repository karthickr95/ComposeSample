@file:Suppress("unused")

package com.embryo.android.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils

fun String.fromHtml(): CharSequence? {
    if (TextUtils.isEmpty(this)) return null
    val charSequence: CharSequence? = wrappedFromHtml(this)
    if (charSequence == null || TextUtils.isEmpty(charSequence)) return null

    val containsEscape = this.contains(LT_ESCAPE_STRING) &&
        this.contains(GT_ESCAPE_STRING)

    return if (!containsEscape) {
        charSequence
    } else {
        wrappedFromHtml(charSequence.toString())
    }
}

fun String.getSpanned(): Spanned? {
    val spanned = wrappedFromHtml(this)
    if (spanned == null || TextUtils.isEmpty(spanned)) return null

    val containsEscape = this.contains(LT_ESCAPE_STRING) &&
        this.contains(GT_ESCAPE_STRING)

    return if (!containsEscape) {
        spanned
    } else {
        wrappedFromHtml(spanned.toString())
    }
}

private fun wrappedFromHtml(value: String): Spanned? {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            value,
            Html.FROM_HTML_MODE_LEGACY,
        )
    } else Html.fromHtml(value)
}

private const val LT_ESCAPE_STRING: String = "&lt;"
private const val GT_ESCAPE_STRING: String = "&gt;"
