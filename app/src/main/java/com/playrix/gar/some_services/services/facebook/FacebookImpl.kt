package com.playrix.gar.some_services.services.facebook

import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FacebookImpl@Inject constructor(
    @ApplicationContext private val appContext: Context
): FacebookRepo {
    override suspend fun getFacebookString(): String? = suspendCoroutine{ continuations ->
        AppLinkData.fetchDeferredAppLinkData(appContext) {
            continuations.resume(
                it.getFacebookString()
            )
        }
    }

    override fun initMyFb(userId: String, userClient: String) {
        FacebookSdk.apply {
           setCredentials(userId, userClient)
            passContext()
            doBooleanInit()
            makeFullInit()
        }
    }

    private fun AppLinkData?.getFacebookString() =
        this?.targetUri?.toString()

    private fun FacebookSdk.makeFullInit() {
        fullyInitialize()
    }

    private fun FacebookSdk.setCredentials(userId: String, userClient: String) {
        setApplicationId(userId)
        setClientToken(userClient)
    }

    private fun FacebookSdk.passContext() {
        @Suppress("DEPRECATION")
        sdkInitialize(appContext)
    }

    private fun FacebookSdk.doBooleanInit() {
        setAdvertiserIDCollectionEnabled(true)
        setAutoInitEnabled(true)
    }
}