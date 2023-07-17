package com.playrix.gar.some_services.collectors.request

import com.playrix.gar.data.api.data.LookupImageResponse
import com.playrix.gar.data.constants.api.RetrofitInstance
import com.playrix.gar.data.constants.api.RetrofitString.apiKey
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MyPhotosApiImpl @Inject constructor() {
    suspend fun requestPhotos(
        userQuery: String,
        pp: Int = 15,
        p: Int = 1
    ): LookupImageResponse? {
        val lookupImageResponse = createQuery(userQuery, pp, p)

        return if (lookupImageResponse?.body() != null && lookupImageResponse.isSuccessful) {
            lookupImageResponse.body()
        } else {
            null
        }
    }

    private suspend fun createQuery(userQuery: String, pp: Int, p: Int) = try {
        RetrofitInstance.myPhotosApi.getWallpapers(
            apiKey = apiKey,
            userQuery = userQuery,
            imagesCountPerPage = pp,
            pageWithImagesCount = p
        )
    } catch (e: HttpException) {
        null
    } catch (e: IOException) {
        null
    }
}