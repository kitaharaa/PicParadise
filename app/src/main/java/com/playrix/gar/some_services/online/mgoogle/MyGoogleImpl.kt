package com.playrix.gar.some_services.online.mgoogle

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.playrix.gar.some_services.collectors.link.google.RefColl
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyGoogleImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val refColl: RefColl
) : MyGoogleRepo {

    override suspend fun startGoogleCollections(): MutableMap<String, String> =
        suspendCoroutine { continuations ->

            gClient.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    try {
                        when (responseCode) {
                            InstallReferrerClient.InstallReferrerResponse.OK -> {
                                continuations.resume(
                                    refColl.doStringChanging(
                                        gClient?.installReferrer?.installReferrer ?: ""
                                    )
                                )
                            }

                            else -> {
                                continuations.resume(refColl.doStringChanging())
                            }
                        }
                    } catch (e: Exception) {
                        continuations.resume(refColl.doStringChanging())
                    }

                    gClient.endConnection()
                }

                override fun onInstallReferrerServiceDisconnected() {
                    continuations.resume(refColl.doStringChanging())
                }
            })
        }

    private val gClient = InstallReferrerClient.newBuilder(context).build()
}