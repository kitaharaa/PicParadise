package com.playrix.gar.some_services.services.lookuper

import android.content.Context
import com.google.android.material.imageview.ShapeableImageView

interface LookupImageManagerRepo {
    fun setImageAsWallpaper(code: Int, context: Context, imageView: ShapeableImageView?)
    fun setMainFromBitmap(context: Context)
}