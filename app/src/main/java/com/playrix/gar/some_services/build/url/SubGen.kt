package com.playrix.gar.some_services.build.url

import com.playrix.gar.data.api.link.LinkParts
import com.playrix.gar.data.api.link.SecondaryLinkParts
import com.playrix.gar.data.my_enums.e_services.FlyerStrings
import com.playrix.gar.some_services.services.notification.MyNotificationImpl
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class SubGen @Inject constructor(
    private val linkParts: LinkParts,
    private val secondaryLinkParts: SecondaryLinkParts,

    private val myNotificationImpl: MyNotificationImpl
) {
    init {
        myNotificationImpl.initService()
    }

    fun List<String>?.fillSubMap(): MutableMap<String, String> {
        val map = mutableMapOf(
            "sub1" to ((this?.getOrNull(0)) ?: null.toString())
        )

        for (number in 2..10) {
            map += "sub$number" to (this?.getOrNull(number) ?: "")
        }

        return map
    }

    fun putPushParam(stringOrNull: String?) {
        secondaryLinkParts.sideLinksMap["push"] = stringOrNull.toString()

        with(myNotificationImpl) {
            stringOrNull.makeServiceNotification(secondaryLinkParts.sideLinksMap["af_userid"])
        }
    }

    fun defineDataList(facebookString: String?): List<String>? = try {
        if (facebookString != null && facebookString.isNotBlank() && facebookString.isNotEmpty()) {
            linkParts.firstMap[FlyerStrings.CAMPAIGN.afStrings] = facebookString

            facebookString.split("://").getOrNull(1)?.split("_")

        } else {
            val cString = linkParts.firstMap[FlyerStrings.CAMPAIGN.afStrings].toString()

            if (cString != null.toString()) {
                cString.split("_")
            } else {
                null
            }
        }
    } catch (_: Exception) {
        null
    }
}