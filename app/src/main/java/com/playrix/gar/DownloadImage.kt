package com.playrix.gar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.SuccessResult
import com.playrix.gar.data.constants.lookup_items.ImageUrlObject

suspend fun Context.convertImageUrlToBitmap(): Bitmap {
    with(this) {
        val imageRequest = coil.request.ImageRequest.Builder(this).data(ImageUrlObject.imageUrl).build()

        val drawableResult = (ImageLoader(this).execute(imageRequest) as SuccessResult).drawable

        return (drawableResult as BitmapDrawable).bitmap
    }
}