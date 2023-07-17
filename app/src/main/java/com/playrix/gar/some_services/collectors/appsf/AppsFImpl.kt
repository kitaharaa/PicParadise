package com.playrix.gar.some_services.collectors.appsf

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.playrix.gar.data.my_enums.e_services.FlyerStrings
import com.playrix.gar.data.my_enums.e_services.MyBaseEnum
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ObfustringThis
class AppsFImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : AppsFRepo {
    override suspend fun getAppsMap(activityContext: Context): MutableMap<String, String> =
        suspendCoroutine { continuations ->

            fun AppsFlyerConversionListener.initAppsInstance() {
                AppsFlyerLib.getInstance().init(
                    MyBaseEnum.APPS_STRING.baseString, this, activityContext
                ).start(activityContext)
            }

            object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(fResponse: MutableMap<String, Any>?) {
                    continuations.resume(stringMap(fResponse))
                }

                override fun onConversionDataFail(fResponse: String?) {
                    continuations.resume(stringMap())
                }

                override fun onAppOpenAttribution(fResponse: MutableMap<String, String>?) {
                    continuations.resume(stringMap())
                }

                override fun onAttributionFailure(fResponse: String?) {
                    continuations.resume(stringMap())
                }
            }.initAppsInstance()
        }

    fun stringMap(map:  MutableMap<String, Any>? = null): MutableMap<String, String> {
        val parsedMap = mutableMapOf<String, String>()

        FlyerStrings.values().forEach{ flyerKey ->
            parsedMap[flyerKey.afStrings] = map?.get(flyerKey.afStrings).toString()
        }

        return parsedMap
    }

    override fun provideUserId() = AppsFlyerLib.getInstance().getAppsFlyerUID(applicationContext)
}