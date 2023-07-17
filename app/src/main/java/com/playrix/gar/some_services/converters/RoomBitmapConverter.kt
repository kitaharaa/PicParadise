package com.playrix.gar.some_services.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class RoomBitmapConverter {

    @TypeConverter
    fun convertFromBitmap(imageBitmap: Bitmap): ByteArray {
        val myOutputStream = ByteArrayOutputStream()

        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, myOutputStream)

        return myOutputStream.toByteArray()
    }

    @TypeConverter
    fun convertFromByteArray(imageByteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
    }
}