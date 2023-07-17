package com.playrix.gar.some_services.build.user_image.image_permission

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.data.photo.ImageCreationTemporaryParams
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ObfustringThis
class ImageCoordinatorRequestEnd @Inject constructor(
    @ActivityContext private val activityContext: Context,
    private val imageRequestEndHandler: ImageRequestEndHandler,
    private val imageCreationTemporaryParams: ImageCreationTemporaryParams
) {
    private val containerInst get() = activityContext as AppCompatActivity

    init {
        imageRequestEndHandler.apply {
            containerInst.initMyLauncher()
        }
    }
    fun processTheResult(processSuccessfulResult: Boolean = true) {
        begForPermissions(processSuccessfulResult)
    }

    private fun begForPermissions(permission: Boolean) {
        with(imageCreationTemporaryParams) {
            mImageIntents = null
            if (permission) {
                myCreatedImageFile = null
                mImageIntents = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                try {
                    myCreatedImageFile = createNewFile()
                    mImageIntents?.putExtra("PhotoPath", mFileName)
                } catch (_: IOException) {
                }

                imageRequestEndHandler.addIntentParams(containerInst)
            }

            imageRequestEndHandler.runLauncher()
        }
    }

    @Throws(IOException::class)
    fun createNewFile(): File {
        return buildResultString()
    }

    private fun buildResultString(): File {
        return File.createTempFile(
            "JPEG_${imageFileName()}_", ".jpg", containerInst.extractDir()
        )
    }

    private fun AppCompatActivity.extractDir() =  getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    private fun imageFileName() =  SimpleDateFormat(
        "yyyyMMdd_HHmmss",
        Locale.getDefault()
    ).format(Date())
}