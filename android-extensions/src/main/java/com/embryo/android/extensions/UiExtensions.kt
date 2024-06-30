@file:JvmName("UiExtensions")
@file:Suppress("unused")

package com.embryo.android.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import kotlin.math.abs

@Suppress("NOTHING_TO_INLINE")
inline fun View.isVisible(): Boolean = visibility == View.VISIBLE

@Suppress("NOTHING_TO_INLINE")
inline fun View.isGone(): Boolean = visibility == View.GONE

@Suppress("NOTHING_TO_INLINE")
inline fun View.visible() {
    visibility = View.VISIBLE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.invisible() {
    visibility = View.INVISIBLE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.gone() {
    visibility = View.GONE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.showOrHide(show: Boolean) {
    if (show) {
        this.visible()
    } else this.gone()
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.invisibleOrGone(invisible: Boolean) {
    if (invisible) {
        this.invisible()
    } else this.gone()
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.visibleOrInvisible(show: Boolean) {
    if (show) {
        this.visible()
    } else this.invisible()
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.toggleVisibility() {
    if (this.isVisible()) this.gone() else this.visible()
}

@Suppress("NOTHING_TO_INLINE")
@ColorInt
inline fun Context.getColorResCompat(@AttrRes id: Int, @ColorRes defaultColorResId: Int): Int {
    val resolvedAttr = TypedValue()
    val resolved = theme.resolveAttribute(id, resolvedAttr, true)
    val colorId = if (!resolved) defaultColorResId else resolvedAttr.resourceId
    return ContextCompat.getColor(this, colorId)
}

@Suppress("NOTHING_TO_INLINE")
@ColorInt
inline fun Context.getThemeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.setFocusAndSelection() {
    if (this is EditText) {
        this.setCursorAtEnd()
    }
    this.requestFocus()
}

@Suppress("NOTHING_TO_INLINE")
inline fun EditText.setCursorAtEnd() {
    setSelection(this.text.length)
}

/**
 * Custom recycling function to add child views into viewGroup if not available,
 * if extra available, hide those extra child views.
 *
 * This is to avoid removing and adding view overhead, especially if the viewGroup
 * is a recyclerView item.
 *
 * @param totalChildCount No. of visible child count.
 * @param childLayoutRes Layout resource for creating new child if required.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun ViewGroup.createOrHideChildViews(
    totalChildCount: Int,
    @LayoutRes childLayoutRes: Int,
) {
    this.let {
        val diff = this.childCount - totalChildCount
        when {
            diff > 0 -> // More child, need to hide last few
                for (index in 0 until this.childCount) {
                    val child = getChildAt(index)
                    if (index < totalChildCount) {
                        child.visible()
                    } else
                        child.gone()
                }
            diff < 0 -> {
                // Less child, need to add few
                for (index in 0 until this.childCount) {
                    val child = getChildAt(index)
                    child.visible()
                }
                for (count in 1..abs(diff)) {
                    View.inflate(context, childLayoutRes, this)
                }
            }
            else -> for (index in 0 until this.childCount) {
                val child = getChildAt(index)
                child.visible()
            }
        }
    }
}

/**
 * Custom recycling function to add child views into viewGroup if not available,
 * if extra available, hide those extra child views.
 *
 * This is to avoid removing and adding view overhead, especially if the viewGroup
 * is a recyclerView item.
 *
 * @param totalChildCount No. of visible child count.
 * @param childLayoutRes Layout resource for creating new child if required.
 * @param layoutInflater LayoutInflater to inflate layouts
 * @param attachToRoot should the child view attached to its root parent or not
 */
@Suppress("NOTHING_TO_INLINE")
inline fun ViewGroup.createOrHideChildViews(
    totalChildCount: Int,
    @LayoutRes childLayoutRes: Int,
    layoutInflater: LayoutInflater,
    attachToRoot: Boolean,
) {
    this.let {
        val diff = this.childCount - totalChildCount
        when {
            diff > 0 -> // More child, need to hide last few
                for (index in 0 until this.childCount) {
                    val child = getChildAt(index)
                    if (index < totalChildCount) {
                        child.visible()
                    } else
                        child.gone()
                }
            diff < 0 -> {
                // Less child, need to add few
                for (index in 0 until this.childCount) {
                    val child = getChildAt(index)
                    child.visible()
                }
                for (count in 1..abs(diff)) {
                    val newView = layoutInflater.inflate(childLayoutRes, this, attachToRoot)
                    newView.id = View.generateViewId()
                    this.addView(newView)
                }
            }
            else -> for (index in 0 until this.childCount) {
                val child = getChildAt(index)
                child.visible()
            }
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun EditText.setTextAndSelection(text: String) {
    this.let {
        setText(text)
        if (text.isNotBlank()) {
            setSelection(it.text.length)
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun EditText.setTextFromHtmlAndSelection(text: String) {
    this.let {
        fromHtml(text)
        if (text.isNotBlank()) {
            setSelection(it.text.length)
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.fromHtml(htmlText: String?) {
    this.text = htmlText?.fromHtml()
}

@Suppress("NOTHING_TO_INLINE")
inline fun Button.fromHtml(htmlText: String?) {
    this.text = htmlText?.fromHtml()
}

@Suppress("NOTHING_TO_INLINE")
inline fun EditText.makeNotEditable() {
    isFocusable = false
    isFocusableInTouchMode = false
    isCursorVisible = false
    isLongClickable = false
    setTextIsSelectable(false)
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean = false
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = false
        override fun onDestroyActionMode(mode: ActionMode?) = Unit
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun EditText.makeEditable() {
    isFocusable = true
    isFocusableInTouchMode = true
    isCursorVisible = true
    isLongClickable = true
    customSelectionActionModeCallback = null
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.increaseTouchArea(extraSpace: Int) {
    val parent = this.parent as View
    parent.post {
        val touchableArea = Rect()
        this.getHitRect(touchableArea)
        touchableArea.top -= extraSpace
        touchableArea.bottom += extraSpace
        touchableArea.left -= extraSpace
        touchableArea.right += extraSpace
        parent.touchDelegate = TouchDelegate(touchableArea, this)
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun Activity.setStatusBarColorFromResource(@ColorRes colorRes: Int) {
    setStatusBarColor(ContextCompat.getColor(this, colorRes))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Activity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color
}

@Suppress("NOTHING_TO_INLINE")
inline fun Int.inflateFromResId(
    context: Context,
    parent: ViewGroup,
    attachToRoot: Boolean = false,
): View = LayoutInflater.from(context).inflate(this, parent, attachToRoot)

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.setDrawableLeft(drawable: Drawable?) {
    val drawableTop = this.compoundDrawables.getOrNull(1)
    val drawableRight = this.compoundDrawables.getOrNull(2)
    val drawableBottom = this.compoundDrawables.getOrNull(3)
    this.setCompoundDrawablesWithIntrinsicBounds(
        drawable,
        drawableTop,
        drawableRight,
        drawableBottom,
    )
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.setDrawableRight(drawable: Drawable?) {
    val drawableLeft = this.compoundDrawables.getOrNull(0)
    val drawableTop = this.compoundDrawables.getOrNull(1)
    val drawableBottom = this.compoundDrawables.getOrNull(3)
    this.setCompoundDrawablesWithIntrinsicBounds(
        drawableLeft,
        drawableTop,
        drawable,
        drawableBottom,
    )
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.isTextEmpty(): Boolean {
    return text.isNullOrEmpty()
}

@Suppress("NOTHING_TO_INLINE")
inline fun Dialog.dismissSafely() {
    try {
        dismiss()
    } catch (_: Exception) {
    }
}

@JvmName("updateLayoutParamsTyped")
inline fun <reified T : ViewGroup.MarginLayoutParams> View.updateMargin(
    block: T.() -> Unit,
) {
    val params = layoutParams as T
    block(params)
    layoutParams = params
}

@Suppress("NOTHING_TO_INLINE")
inline fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, colorRes))
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.setHeightDimenRes(@DimenRes dimenRes: Int) {
    val param = this.layoutParams
    param.height = resources.getDimensionPixelOffset(dimenRes)
    this.layoutParams = param
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.setWidthDimenRes(@DimenRes dimenRes: Int) {
    val param = this.layoutParams
    param.width = this.context.resources.getDimensionPixelOffset(dimenRes)
    this.layoutParams = param
}

@Suppress("NOTHING_TO_INLINE")
inline fun ProgressBar.setProgressBarProgress(progress: Int, animate: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && animate) {
        this.setProgress(progress, true)
    } else
        this.progress = progress
}