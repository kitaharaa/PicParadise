package com.playrix.gar.some_services.services.facebook

interface FacebookRepo {
    suspend fun getFacebookString(): String?

    fun initMyFb(
        userId: String, userClient: String
    )
}
