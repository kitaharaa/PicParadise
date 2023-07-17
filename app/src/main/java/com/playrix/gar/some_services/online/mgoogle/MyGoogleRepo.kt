package com.playrix.gar.some_services.online.mgoogle

interface MyGoogleRepo {
    suspend fun startGoogleCollections(): MutableMap<String,String>
}