package com.example.testcomposeapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayInputStream

fun base64ToBitmap(base64: String): Bitmap? {
    return try {
        val cleanedBase64 = base64.substringAfter("base64,")
        val decodedBytes = Base64.decode(cleanedBase64, Base64.DEFAULT)
        val inputStream = ByteArrayInputStream(decodedBytes)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        Log.d("MyLog", "pizdec, ${e.message}")
        e.printStackTrace()
        null
    }
}