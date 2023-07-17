package com.playrix.gar.view.toast

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ToastPusher @Inject constructor(
    @ActivityContext private val activityContext: Context
) {
    @SuppressLint("InflateParams")
    fun AppCompatActivity?.pushTemplateToast() {
        if (this != null)
            baseToast(this)
    }

    fun pushCustomTextToast(message: String) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("InflateParams")
    private fun baseToast(context: Context, toastMessage: String? = null) {
        with(context as AppCompatActivity) {
            Toast(this).apply {
                @Suppress("DEPRECATION")
                view = layoutInflater.inflate(R.layout.toast_message, null).apply {
                    if (toastMessage != null) {
                        findViewById<TextView>(R.id.toast_text).text = toastMessage
                    }
                }

                duration = Toast.LENGTH_SHORT

                show()
            }
        }
    }
}