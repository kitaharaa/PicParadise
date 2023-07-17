package com.playrix.gar.some_services.online

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.playrix.gar.R
import com.playrix.gar.data.my_enums.e_services.ConfigEnum
import javax.inject.Inject

class MyFirebaseImpl @Inject constructor() : MyFirebaseParamsRepo, MyFirebaseUpdatingRepo {
    private val instance = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        })

        setDefaultsAsync(R.xml.config_base)
    }

    override fun getIsAppsFEnable(): Boolean {
        return instance.getBoolean(ConfigEnum.ENABLE_VARIABLE.paramString)
    }

    override fun getTString(): String = ConfigEnum.LINK_START.getFirebaseString()

    override fun getIdString(): String  = ConfigEnum.USER_IDS_VARIABLE.getFirebaseString()

    override fun getCtString(): String  = ConfigEnum.TOKEN_VARIABLE.getFirebaseString()
    override fun getFDecString(): String = ConfigEnum.FDEC_VARIABLE.getFirebaseString()

    override fun updateParams(paramsUpdatingListener: OnCompleteListener<Boolean>) {
        instance.fetchAndActivate().addOnCompleteListener(paramsUpdatingListener)
    }

    private fun ConfigEnum.getFirebaseString() = instance.getString(paramString)
}