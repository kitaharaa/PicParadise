package com.playrix.gar.some_services.services.downloader

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class DownloadingManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : DownloadingManagerRepo {

    private var urlToDownload: String? = null

    private val writePermission get() = Manifest.permission.WRITE_EXTERNAL_STORAGE

    private var writeExternalStorageLauncher: ActivityResultLauncher<String>? = null

    private var activityContext: Context? = null

    fun initLauncher(instance: AppCompatActivity?) {

        activityContext = instance
        writeExternalStorageLauncher = instance?.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            when (it) {
                true -> makeDownloading()
                else -> pushToast("Write permission failed")
            }
        }
    }

    override fun downloadImageFromUrl(activityContext: Context, imageUrl: String) {
        urlToDownload = imageUrl

        if (ContextCompat.checkSelfPermission(
                activityContext,
                writePermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            makeDownloading()

        } else writeExternalStorageLauncher?.launch(writePermission)

    }

    override fun saveLikedBitmap(bitmap: Bitmap?) {
        pushToast("Liked downloading started")

        if (bitmap != null) {
            try {
                val filePath = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    getImageDirectory("liked_photo_${System.currentTimeMillis()}")
                )

                FileOutputStream(filePath).apply {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
                    flush()
                    close()
                }

                pushToast("Liked downloading ended")
            } catch (e: Exception) {
                e.printStackTrace()
                pushToast("Liked downloading failed")
            }
        } else pushToast("Image does not exist")
    }

    private fun makeDownloading() {
        try {
            pushToast("Downloading started")
            val dManager =
                applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val downloadRequest = DownloadManager.Request(urlToDownload?.parseToUri()).apply {
                setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI
                )
                    .setMimeType("image_shapeable/*")
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("Downloading a searched photo")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        getImageDirectory("searched_photo")
                    )
            }

            dManager.enqueue(downloadRequest)

            pushToast("Downloading ended")
        } catch (e: Exception) {
            e.printStackTrace()

            pushToast("Downloading failed")
        }
    }

    private fun getImageDirectory(downloadFrom: String) = File.separator + downloadFrom + ".jpg"

    private fun pushToast(message: String) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun String.parseToUri(): Uri? = Uri.parse(this)
}