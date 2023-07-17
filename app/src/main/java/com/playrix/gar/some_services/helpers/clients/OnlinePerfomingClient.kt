package com.playrix.gar.some_services.helpers.clients

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class OnlinePerfomingClient @Inject constructor(
    private val performingHandler: PerformingHandler
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        if (view?.title?.contains("Profound6394") == true) {
            performingHandler.loadApp()
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val pageUrl = request?.url?.toString() ?: return false
        return try {
            if (pageUrl.startsWith("https://t.me/joinchat")) {
                performingHandler.openTeleg(Uri.parse(pageUrl))
            }
            when {
                pageUrl.startsWith("http://") || pageUrl.startsWith("https://") -> false
                else -> {
                    manageServices(pageUrl)
                    true
                }
            }
        } catch (e: Exception) {
            true
        }
    }

    private fun manageServices(pageUrl: String) {
        when {
            pageUrl.startsWith("mailto:") -> {
                performingHandler.openMessages(pageUrl)
            }

            pageUrl.startsWith("tel:") -> {
                performingHandler.openPhoneCall(pageUrl)
            }
        }

        performingHandler.openViberForExample(pageUrl)
    }
}