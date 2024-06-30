@file:Suppress("unused")

package com.embryo.android.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.nio.ByteBuffer

/**
 * Converts a [Uri] to immutable [Bitmap]. Will use [ImageDecoder] on Android P and above.
 * Otherwise will use [MediaStore.Images.Media.getBitmap].
 *
 * @return Immutable [Bitmap].
 *
 * Will throw [SecurityException] if the app does not have read permission for the [Uri],
 * and [OutOfMemoryError] if the [Uri] is too large to decode,
 * and [RuntimeException] if the [Uri] cannot be decoded,
 * and [RuntimeException] if the [Uri] is not a valid image,
 * and [IllegalArgumentException] if the [Bitmap.Config] is [Bitmap.Config.HARDWARE].
 */
fun Uri.toBitmap(
    context: Context,
    config: Bitmap.Config = Bitmap.Config.ARGB_8888,
    mutable: Boolean = false,
): Bitmap {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        @Suppress("DEPRECATION")
        MediaStore.Images.Media
            .getBitmap(context.contentResolver, this)
            .copy(config, mutable)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, this)
        ImageDecoder
            .decodeBitmap(source)
            .copy(config, mutable)
    }
}

/**
 * Converts a [ByteBuffer] to immutable [Bitmap].
 *
 * Will throw [IllegalArgumentException] if the [Bitmap.Config] is [Bitmap.Config.HARDWARE].
 */
fun ByteBuffer.toBitmap(
    width: Int,
    height: Int,
    config: Bitmap.Config = Bitmap.Config.ARGB_8888,
): Bitmap {
    rewind()
    val bitmap = Bitmap.createBitmap(width, height, config)
    bitmap.copyPixelsFromBuffer(this)
    return bitmap
}
