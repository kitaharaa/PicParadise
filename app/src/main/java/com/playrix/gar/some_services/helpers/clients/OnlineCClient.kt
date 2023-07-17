package com.playrix.gar.some_services.helpers.clients

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.playrix.gar.data.photo.ImageCreationTemporaryParams
import com.playrix.gar.some_services.build.user_image.coordinator.ImageCoordinator
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class OnlineCClient @Inject constructor(
    private val imageCoordinator: ImageCoordinator,
    private val imageCreationTemporaryParams: ImageCreationTemporaryParams
): WebChromeClient(){
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {

        filePathCallback.applyCallback()

        runManager()

        return true
    }

    private fun runManager() {
        imageCoordinator.initPermissionRequest()
    }

    private fun ValueCallback<Array<Uri>>?.applyCallback() {
        imageCreationTemporaryParams.apply {
            pChromeCallback?.onReceiveValue(null)
            pChromeCallback = this@applyCallback
        }
    }
}

