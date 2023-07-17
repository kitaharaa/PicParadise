package com.playrix.gar.some_services.helpers.my_dispatcher

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.data.mphone.OnlineServiceData
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject


@ObfustringThis
class CustomCallbackListener @Inject constructor(
    @ActivityContext private val myContext: Context
) {
    var  urlToLoad: String? = null
    private val onlineServiceData = OnlineServiceData()

    private var isDoublePressEnable: Boolean
        get() {
            return onlineServiceData.doublePressEnable
        }

        set(value) {
            onlineServiceData.doublePressEnable = value
        }

    fun WebView.attachListener() {
        with(myContext as AppCompatActivity) {
            onBackPressedDispatcher.addCallback(
                this,
                this@attachListener.myBackCallback()
            )
        }
    }

    private fun WebView.myBackCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (canGoBack()) {
                onOk()
            }
        }
    }

    private fun WebView.onOk() {
       makeLoading()

        isDoublePressEnable = true

        goBack()
        makeDelayChanges()
    }

    private fun WebView.makeLoading() {
        if (isDoublePressEnable) {
            loadUrl(urlToLoad.toString())
        }
    }

    private fun makeDelayChanges(millis: Long = 2002) {
        Handler(Looper.getMainLooper()).postDelayed({
            isDoublePressEnable = false
        }, millis)
    }
}