package com.kappdev.wordbook.feature_dictionary.domain.util

import android.graphics.Bitmap

fun Bitmap.resize(maxWidth: Int = reqWidth, maxHeight: Int = reqHeight): Bitmap {
    return if (maxHeight > 0 && maxWidth > 0) {
        val ratioBitmap = this.width.toFloat() / this.height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
        var finalWidth = maxWidth
        var finalHeight = maxHeight
        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }
        Bitmap.createScaledBitmap(this, finalWidth, finalHeight, true)
    } else {
        this
    }
}

private const val reqWidth = 500
private const val reqHeight = 500

