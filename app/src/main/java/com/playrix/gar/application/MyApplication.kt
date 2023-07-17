package com.playrix.gar.application

import android.app.Application
import com.playrix.gar.data.constants.shared_preferences.MySharedPreferences
import com.playrix.gar.some_services.pusher.NotificationAnalytic
import com.playrix.gar.view.online_services.OnlineServicesActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MySharedPreferences.initSp(this)
        NotificationAnalytic.init<OnlineServicesActivity>(this)
    }
}