package com.playrix.gar.some_services.collectors.link.google

import com.playrix.gar.data.api.link.LinkParts
import com.playrix.gar.data.my_enums.e_services.FlyerStrings
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import org.json.JSONObject
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@ObfustringThis
class RefColl @Inject constructor(
    private val linkParts: LinkParts,
    private val dataDecoding: DataDecoding
) {

    fun doStringChanging(referrer: String = ""): MutableMap<String, String> {
        return collections(
            try {
                val dCon: String =
                    URLDecoder.decode(
                        referrer.split("utm_content=")[1],
                        StandardCharsets.UTF_8.displayName()
                    )
                val djf = JSONObject(dCon).opt("source")?.toString() ?: ""
                val source = JSONObject(djf)

                val jObject = dataDecoding.dataA(
                    source.opt("data")?.toString() ?: "",
                    source.opt("nonce")?.toString() ?: "",
                    facebookDec = linkParts.fDec
                )
                jObject
            } catch (_: Exception) {
                null
            }
        )
    }

    private fun collections(resultData: JSONObject?): MutableMap<String, String> {
        val secondaryMap = mutableMapOf<String, String>()

        if (!linkParts.afEnable) {
            secondaryMap += doBaseCollection(resultData)
        }

        secondaryMap[FlyerStrings.ACCOUNT_ID.afStrings] = resultData?.opt("account_id").toString()

        return secondaryMap
    }

    private fun doBaseCollection(jsonData: JSONObject?): Map<String, String> {
        val baseMap = mutableMapOf<String, String>()
        baseMap.doFirstCollection(jsonData)
        baseMap.doSecondaryCollections(jsonData)
        return baseMap
    }

    private fun MutableMap<String, String>.doSecondaryCollections(jsonData: JSONObject?) {
        this += mutableMapOf(
            FlyerStrings.CAMPAIGN.afStrings to
                    jsonData?.opt("campaign_group_name").toString(),
            FlyerStrings.MEDIA_SOURCE.afStrings to
                    jsonData?.opt("is_instagram")?.run {
                        if (this == "true") "Instagram" else "Facebook Ads"
                    }.toString(),
            FlyerStrings.AF_CHANNEL.afStrings to jsonData?.opt("is_instagram")?.run {
                if (this == "true") "Instagram" else "Facebook"
            }.toString()
        )
    }

    private fun MutableMap<String, String>.doFirstCollection(jsonData: JSONObject?) {
        this += mutableMapOf(
            FlyerStrings.ADSET.afStrings to jsonData?.opt("adgroup_name").toString(),
            FlyerStrings.AD_ID.afStrings to jsonData?.opt("ad_id").toString(),
            FlyerStrings.CAMPAIGN_ID.afStrings to
                    jsonData?.opt("campaign_id").toString()
        )
    }
}