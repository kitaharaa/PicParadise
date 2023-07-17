package com.playrix.gar.some_services.helpers.settings

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class OnlineServiceParams@Inject constructor() {
    fun WebSettings.countParams() {
        changeDatabaseParams(this)
        changeImageParams(this)
        changeSavingsParams()
        changeAccessParams(this)
        changeOldParams()
        changeContentParams()
    }

    private fun changeAccessParams(webSettings: WebSettings) {
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.mixedContentMode = 0
    }

    private fun changeDatabaseParams(webSettings: WebSettings) {
        webSettings.databaseEnabled = true

        with(webSettings) {
            userAgentString = this
                .userAgentString
                .replace("; wv", "")
        }
    }

    private fun WebSettings.changeContentParams() {
        useWideViewPort = true
        allowFileAccess = true
        builtInZoomControls = true
    }

    private fun changeImageParams(webSettings: WebSettings) {
        webSettings.loadsImagesAutomatically = true

        webSettings.displayZoomControls = false
    }

    private fun WebSettings.changeSavingsParams() {
        loadWithOverviewMode = true
        allowContentAccess = true
        domStorageEnabled = true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WebSettings.changeOldParams() {
        javaScriptEnabled = true
        javaScriptCanOpenWindowsAutomatically = true

        setSupportMultipleWindows(false)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            @Suppress("DEPRECATION")
            saveFormData = true
        }
    }
}