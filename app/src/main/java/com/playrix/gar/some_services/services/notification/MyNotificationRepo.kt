package com.playrix.gar.some_services.services.notification

interface MyNotificationRepo {
    fun initService()
    fun String?.makeServiceNotification(
        userId: String?
    )
}