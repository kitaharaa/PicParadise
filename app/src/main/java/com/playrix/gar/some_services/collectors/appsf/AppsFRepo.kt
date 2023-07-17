package com.playrix.gar.some_services.collectors.appsf

import android.content.Context
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis

@ObfustringThis
interface AppsFRepo {
    suspend fun getAppsMap(activityContext: Context): MutableMap<String,String>
    fun provideUserId(): String?
}