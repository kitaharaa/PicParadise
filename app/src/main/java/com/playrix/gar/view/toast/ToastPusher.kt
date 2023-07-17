package com.playrix.gar.view.toast

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ToastPusher @Inject constructor(
    @ActivityContext private val activityContext: Context
) {
    fun pushCustomTextToast(message: String) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show()
    }
}