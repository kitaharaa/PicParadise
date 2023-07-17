package com.playrix.gar.some_services.services.lookuper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import com.google.android.material.imageview.ShapeableImageView
import com.playrix.gar.data.constants.lookup_items.RemoveMenuPojo.chosenPojo
import com.playrix.gar.data.constants.lookup_items.PhonePageConstants.HOME_PAGE
import javax.inject.Inject

class LookupImageManager @Inject constructor() : LookupImageManagerRepo {
    override fun setImageAsWallpaper(code: Int, context: Context, imageView: ShapeableImageView?) {
        try {
            if (imageView?.drawable == null) {
                fillToast(context, "Wait, downloading")
            } else {
                initWallpaperManagerInstance(context, (imageView.drawable as BitmapDrawable).bitmap, code)
            }
        } catch (e: Exception) {
            fillToast(context, "Try Again")
        }
    }

    override fun setMainFromBitmap(context: Context) {
        val bitmapImage = chosenPojo?.photoBitmap

        if (bitmapImage != null) {
            try {
                initWallpaperManagerInstance(context, bitmapImage, HOME_PAGE)
            } catch (e: Exception) {
                fillToast(context, "System wallpaper error")
            }
        } else {
            fillToast(context, "Image does not exist")
        }
    }

    private fun fillToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun initWallpaperManagerInstance(context: Context, imageBitmap: Bitmap?, code: Int) {
        WallpaperManager.getInstance(context).setBitmap(imageBitmap, null, true, code)

        fillToast(context, "Done")
    }
}