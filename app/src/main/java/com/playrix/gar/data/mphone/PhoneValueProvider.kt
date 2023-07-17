package com.playrix.gar.data.mphone

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.provider.Settings
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PhoneValueProvider @Inject constructor(@ApplicationContext private val context: Context) {

    inner class DeveloperDefinition {
        private val myResolver get() = context.contentResolver
        private val devSettings get() = Settings.Global.DEVELOPMENT_SETTINGS_ENABLED

        fun callValue() = try {
            Settings.Global
                .getInt(
                    myResolver,
                    devSettings,
                    0
                ) != 0
        } catch (e: Exception) {
            true
        }.toString()
    }

    inner class GoogleDefinition {
        private val userId get() = AdvertisingIdClient.getAdvertisingIdInfo(context).id

        suspend fun getId() = suspendCoroutine { cont ->
            try {
                cont.resume(userId)
            } catch (e: GooglePlayServicesRepairableException) {
                cont.resume(getMyNull())
            } catch (e: GooglePlayServicesNotAvailableException) {
                cont.resume(getMyNull())
            } catch (e: Exception) {
                cont.resume(getMyNull())
            }
        }

        private fun getMyNull() = null
    }

    inner class BatteryDefinition {
        fun getNumber(): Float {
            return try {
                myRegisterReceiver.let { letIntent ->
                    if (letIntent != null) {
                        letIntent.intentLevel() * getFullPercentage() / provideScaleLevel(letIntent)
                    } else getFullPercentage()
                }
            } catch (e: Exception) {
                getFullPercentage()
            }
        }

        private fun getFullPercentage() = 100F

        private val myRegisterReceiver
            get() = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

        private fun Intent.intentLevel() = getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        private fun provideScaleLevel(intent: Intent) = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }
}