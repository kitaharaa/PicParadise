package com.playrix.gar.some_services.helpers.settings

import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.some_services.helpers.clients.OnlineCClient
import com.playrix.gar.some_services.helpers.clients.OnlinePerfomingClient
import com.playrix.gar.view.toast.ToastPusher
import javax.inject.Inject

class OnlineSetupHelper @Inject constructor(
    private val onlineServiceParams: OnlineServiceParams,
    private val onlineCClient: OnlineCClient,
    private val onlinePerfomingClient: OnlinePerfomingClient,
    private val toastPusher: ToastPusher
) {

    fun AppCompatActivity.fullyInitOnlineService(
        urlToLoad: String,
        view: WebView
    ) {
        view.applySettings()
        view.setClients()

        view.loadUrl(urlToLoad)

        toastPushing()
    }

    private fun AppCompatActivity.toastPushing() {
        toastPusher.apply {
            pushTemplateToast()
        }
    }

    private fun WebView.applySettings() {
        with(settings) {
            onlineServiceParams.apply { countParams() }
        }
    }

    private fun WebView.setClients() {
             attachChrome()
        attachView()
    }

    private fun WebView.attachView() {
        webViewClient = onlinePerfomingClient
    }

    private fun WebView.attachChrome() {
        webChromeClient = onlineCClient
    }
}