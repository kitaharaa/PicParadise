package com.playrix.gar.some_services.build.url.m_string

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ObfustringThis
open class ToStringConverter {
    fun MutableMap<String, String>.makeConversion(includeEncoding: Boolean = false): String {
        val sb = StringBuilder()
        sb.makeCustomForEach(this, includeEncoding)
        return sb.toString()
    }

    private fun StringBuilder.makeCustomForEach(
        mutableMap: MutableMap<String, String>,
        includeEncoding: Boolean
    ) {
        mutableMap.forEach {
            append(
                "${it.key}=${
                    if (!includeEncoding) {
                        if (it.key == "battery") {
                            it.value.makeEncoding()
                        } else {
                            it.value
                        }
                    } else {
                        it.value.makeEncoding()
                    }
                }&"
            )
        }
    }

    private fun String.makeEncoding(): String {

        return try {
            URLEncoder.encode(this, StandardCharsets.UTF_8.displayName())
        } catch (e: Exception) {
            null.toString()
        }
    }
}

