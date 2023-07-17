package com.playrix.gar.some_services.build.user_image.image_permission

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.playrix.gar.data.photo.ImageCreationTemporaryParams
import com.playrix.gar.some_services.build.user_image.ResutManager
import java.io.File
import javax.inject.Inject

class ImageRequestEndHandler @Inject constructor(
    private val imageCreationTemporaryParams: ImageCreationTemporaryParams,
    private val resultManager: ResutManager
) {
    private var customLauncher: ActivityResultLauncher<Intent>?  = null

    fun AppCompatActivity.initMyLauncher() {
        customLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            resultManager.onReceiveFinalResult(result)
        }
    }

    fun addIntentParams(
        containerInst: AppCompatActivity
    ) {
        with(imageCreationTemporaryParams) {
            val bImageFile = myCreatedImageFile
            if (bImageFile != null) {

                mFileName = "file:${bImageFile.absolutePath}"
                val fileUriProvider =

                containerInst.getUri(bImageFile)
                mImageIntents?.putParams(fileUriProvider)
            } else {
                mImageIntents = null
            }
        }
    }

    private fun getDataExtractionIntent() = Intent(Intent.ACTION_GET_CONTENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "image/*"
    }

    private fun Intent?.getCoupleIntent(): Array<Intent?> = this?.let { intents ->
        arrayOf(intents)
    } ?: arrayOfNulls(0)

    private fun AppCompatActivity.getUri(bImageFile: File): Uri? {
        return FileProvider.getUriForFile(
            this, "${this.packageName}.provider", bImageFile
        )
    }

    private fun Intent?.putParams(fileUriProvider: Uri?) {
        this?.apply {
            putExtra(MediaStore.EXTRA_OUTPUT, fileUriProvider)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    fun runLauncher() {
        Intent(Intent.ACTION_CHOOSER).run {
            putExtra(Intent.EXTRA_TITLE, "Image Chooser")
            putExtra(Intent.EXTRA_INTENT, getDataExtractionIntent())
            putExtra(Intent.EXTRA_INITIAL_INTENTS, imageCreationTemporaryParams.mImageIntents.getCoupleIntent())
            customLauncher?.launch(this)
        }
    }
}