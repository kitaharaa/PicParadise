package com.playrix.gar.some_services.collectors.link.google

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import org.json.JSONObject
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@ObfustringThis
class DataDecoding @Inject constructor() {
    private val noPadding = "AES/GCM/NoPadding"

    fun dataA(data: String, nonce: String, facebookDec: String): JSONObject? {
        fun String?.dataDecoding(): ByteArray? {
            val len = this?.length ?: return null
            return if (len % 2 == 0) {
                this.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
            } else null
        }

        return try {
            val tg = SecretKeySpec(facebookDec.dataDecoding(), noPadding)
            val cp: Cipher? = Cipher.getInstance(noPadding)
            cp?.init(Cipher.DECRYPT_MODE, tg, IvParameterSpec(nonce.dataDecoding()))

            if (cp != null) {
                JSONObject(String(cp.doFinal(data.dataDecoding())))
            } else null

        } catch (_: Exception) {
            null
        }
    }
}
