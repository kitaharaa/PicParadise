package com.playrix.gar.some_services.pusher

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.onesignal.OneSignal
import com.playrix.gar.data.constants.shared_preferences.MySharedPreferences.providedSavedString
import com.playrix.gar.data.my_enums.NotificationEnum
import com.playrix.gar.data.my_enums.e_intent.BaseIntents
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ObfustringThis
object NotificationAnalytic {
    var signalValue: String = NotificationEnum.FIRST_OPEN.signValue
    var pushIntent: Intent? = null

    private const val customFlags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_NEW_TASK

    private val camp: String = "&campaign="
    private val subTen: String = "&sub10="

    inline fun <reified T : ComponentActivity> init(
        application:
        Application
    ) {
        OneSignal.setNotificationOpenedHandler {
            signalValue = NotificationEnum.PUSH.signValue

            pushIntent = Intent(application, T::class.java).performIntentFilling()
        }
    }

    fun Context.navigateWithCheckPush(
        activityIntent: Intent? = null,
        isCache: Boolean = false,
        onFinish: (() -> Unit)? = null
    ) {
        CoroutineScope(SupervisorJob()).launch(Dispatchers.Main.immediate) {
            if (isCache) delay(1000)
            if (pushIntent != null) {
                startActivity(pushIntent)
                pushIntent = null
                onFinish?.invoke()
            } else {
                activityIntent?.let {
                    startActivity(activityIntent)
                    onFinish?.invoke()
                }
            }
        }
    }

    fun changeLink(link: String): String {

        val sub = provideSubTenExtraction(link)
        return if (sub.isEmpty())
            link.replace(subTen, subTen + signalValue)
        else {
            val oldValue = subTen + sub
            val newValue = subTen + signalValue
            link.replace(oldValue, newValue)
        }
    }

    private fun provideSubTenExtraction(link: String): String {
        val toCampaignCropped = link.substringBefore(camp)

        return toCampaignCropped.substringAfter(subTen)
    }

    private fun getFilledLink()  = changeLink(providedSavedString)

    fun Intent.performIntentFilling(): Intent {
        flags = customFlags
        putExtra(
            BaseIntents.URL_TO_LOAD.intentString,
            getFilledLink()
        )

        return this
    }
}