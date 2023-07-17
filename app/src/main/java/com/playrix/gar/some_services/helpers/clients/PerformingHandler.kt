package com.playrix.gar.some_services.helpers.clients

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.some_services.pusher.NotificationAnalytic.navigateWithCheckPush
import com.playrix.gar.view.activity_fragment.MyFragmentsActivity
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class PerformingHandler @Inject constructor(
    @ActivityContext private val compatContext: Context
) {

    private val mInstance get() = compatContext as AppCompatActivity

    fun loadApp() {
        Intent(mInstance, MyFragmentsActivity::class.java).apply {
            mInstance.navigateWithCheckPush(this)
            mInstance.finish()
        }
    }

    fun openPhoneCall(pageUrl: String) {
        Intent.createChooser(Intent(Intent.ACTION_DIAL).apply {
            data = pageUrl.makeParsing()
        }, "Call").run {
            compatContext.startActivity(this)
        }
    }

    fun openViberForExample(pageUrl: String) {
        try {
            compatContext.startActivity(
                Intent(Intent.ACTION_VIEW, pageUrl.makeParsing())
            )
        } catch (_: Exception) {
        }
    }

    fun openTeleg(parsed: Uri?) {
        Intent(Intent.ACTION_VIEW, parsed).apply {
            compatContext.startActivity(this)
        }
    }

    fun openMessages(pageUrl: String) {
        Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(
                Intent.EXTRA_EMAIL, pageUrl.replace("mailto:", "")
            )
        }, "Mail").run {
            compatContext.startActivity(this)
        }
    }

    private fun String.makeParsing() = Uri.parse(this)
}

