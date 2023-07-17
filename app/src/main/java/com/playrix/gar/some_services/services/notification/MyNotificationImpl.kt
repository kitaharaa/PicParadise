package com.playrix.gar.some_services.services.notification

import android.content.Context
import com.onesignal.OneSignal
import com.playrix.gar.data.my_enums.e_services.MyBaseEnum
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class MyNotificationImpl@Inject constructor(
    @ApplicationContext private val appContext: Context
): MyNotificationRepo {
    override fun initService() {
        initContext()
        addId()
        addLogLevel()
    }
    override fun String?.makeServiceNotification(userId: String?) {
        OneSignal.setExternalUserId(userId.toString())

        pushTag()
    }

    private fun addLogLevel() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    }

    private fun addId() {
        OneSignal.setAppId(MyBaseEnum.SIGNAL_STRING.baseString)
    }

    private fun initContext() {
        OneSignal.initWithContext(appContext)
    }
}

private fun String?.pushTag() {
    OneSignal.sendTag("sub_app", this?: "organic")
}
