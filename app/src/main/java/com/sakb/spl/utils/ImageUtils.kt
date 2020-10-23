package com.sakb.spl.utils

import android.graphics.Bitmap
import android.graphics.Matrix

object ImageUtils {
     fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }
}