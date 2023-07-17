package com.playrix.gar.some_services.build.url

import com.playrix.gar.data.api.link.LinkParts
import com.playrix.gar.data.api.link.SecondaryLinkParts
import com.playrix.gar.some_services.build.url.m_string.ToStringConverter
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class GenerateLink @Inject constructor(
    private val linkParts: LinkParts,
    private val secondaryLinkParts: SecondaryLinkParts,
    private val subGen: SubGen
) : ToStringConverter() {

    fun makeUrl(): String {
        val url = StringBuilder(linkParts.stringEntry)

        url.makeFullUrlFilling(subGen.defineDataList(linkParts.facebookDeep))

        return url.toString()
    }

    private fun StringBuilder.fillUrl(paramsMap: MutableMap<String, String>) {
        append(paramsMap.makeConversion())
        append(linkParts.firstMap.makeConversion(includeEncoding = true))
        append(secondaryLinkParts.sideLinksMap.makeConversion())
    }

    private fun StringBuilder.makeFullUrlFilling(stringList: List<String>?) {
        with(subGen) {
            val paramsMap = try {
                stringList.fillSubMap()
            } catch (e: Exception) {
                e.printStackTrace()

                mutableMapOf()
            }

            putPushParam(stringList?.getOrNull(1))

            fillUrl(paramsMap)
        }
    }
}