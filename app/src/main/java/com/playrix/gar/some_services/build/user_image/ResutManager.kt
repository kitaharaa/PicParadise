package com.playrix.gar.some_services.build.user_image

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.playrix.gar.data.photo.ImageCreationTemporaryParams
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class ResutManager @Inject constructor(
    private val imageCreationTemporaryParams: ImageCreationTemporaryParams
) {

    fun onReceiveFinalResult(resultInstance: ActivityResult?) {
        var array: Array<Uri>? = null
        with(imageCreationTemporaryParams) {
            if (resultInstance?.resultCode == Activity.RESULT_OK) {
                if (pChromeCallback == null) return

                if (resultInstance.data == null || resultInstance.data?.data == null) {
                    if (mFileName != null) {
                        array = arrayOf(Uri.parse(mFileName))
                        mFileName = null
                    }
                } else {
                    resultInstance.data?.dataString?.let {
                        array = arrayOf(Uri.parse(it))
                    }
                }
            }
        }

        updateCallback(array)
    }

    private fun updateCallback(array: Array<Uri>?) {
        with(imageCreationTemporaryParams) {
            pChromeCallback?.onReceiveValue(array)
            pChromeCallback = null
        }
    }
}