package com.playrix.gar.some_services.services.downloader

import android.content.Context
import android.graphics.Bitmap

interface DownloadingManagerRepo {
    fun downloadImageFromUrl(activityContext: Context, imageUrl: String)
    fun saveLikedBitmap(bitmap: Bitmap?)
}