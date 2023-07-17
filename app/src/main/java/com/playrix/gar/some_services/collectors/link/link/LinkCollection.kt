package com.playrix.gar.some_services.collectors.link.link

import android.content.Context
import com.playrix.gar.data.api.link.LinkParts
import com.playrix.gar.data.api.link.SecondaryLinkParts
import com.playrix.gar.data.mphone.PhoneValueProvider
import com.playrix.gar.data.my_enums.e_services.MyBaseEnum
import com.playrix.gar.some_services.services.facebook.FacebookImpl
import com.playrix.gar.some_services.build.url.GenerateLink
import com.playrix.gar.some_services.collectors.appsf.AppsFImpl
import com.playrix.gar.some_services.online.mgoogle.MyGoogleImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class LinkCollection @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val linkParts: LinkParts,
    private val secondaryLinkParts: SecondaryLinkParts,
    private val appsFImpl: AppsFImpl,
    private val myGoogleImpl: MyGoogleImpl,
    private val facebookImpl: FacebookImpl,
    private val phoneValueProvider: PhoneValueProvider,
    private val generateLink: GenerateLink
) {
    suspend fun collectAll(context: Context): String {
        doMainCollection(context)
        secondaryLinkParts.sideLinksMap += doSideCollection()

        return generateLink.makeUrl()
    }

    private suspend fun doSideCollection(): MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        map += firstMapPart() + secondCollections()
        return map
    }

    private fun secondCollections(): MutableMap<String, String> =
        mutableMapOf(
            "bundle" to appContext.packageName,
            "push" to null.toString(),
            "dev_key" to MyBaseEnum.APPS_STRING.baseString,
            "fb_app_id" to linkParts.cid,
            "fb_at" to linkParts.fClient
        )

    private suspend fun firstMapPart(): MutableMap<String, String> = mutableMapOf(
        "google_adid" to phoneValueProvider.GoogleDefinition().getId().toString(),
        "af_userid" to appsFImpl.provideUserId().toString(),
        "adb" to phoneValueProvider.DeveloperDefinition().callValue(),
        "battery" to phoneValueProvider.BatteryDefinition().getNumber().toString(),
    )

    private suspend fun doMainCollection(context: Context) {
        linkParts.apply {
            if (afEnable) {
                firstMap += appsFImpl.getAppsMap(context)
            }
            firstMap += myGoogleImpl.startGoogleCollections()
            facebookDeep = facebookImpl.getFacebookString()
        }
    }
}