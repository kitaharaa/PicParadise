package com.playrix.gar.some_services.collectors.request

import com.playrix.gar.data.api.data.LookupImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyPhotosApi {

    @GET("search")
    suspend fun getWallpapers(
        @Header("Authorization") apiKey: String,
        @Query("query") userQuery: String,
        @Query("page") pageWithImagesCount: Int = 1,
        @Query("per_page") imagesCountPerPage: Int = 15
    ): Response<LookupImageResponse>
}