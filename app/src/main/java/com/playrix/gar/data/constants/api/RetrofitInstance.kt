package com.playrix.gar.data.constants.api

import com.playrix.gar.data.constants.api.RetrofitString.baseUrl
import com.playrix.gar.some_services.collectors.request.MyPhotosApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val converter = GsonConverterFactory.create()

    val myPhotosApi: MyPhotosApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converter)
        .build()
        .create(MyPhotosApi::class.java)
}