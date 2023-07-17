package com.playrix.gar.viewmodels

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playrix.gar.data.api.data.LookupImageResponse
import com.playrix.gar.data.room.entity.LikedPhotos
import com.playrix.gar.data.room.PhotosDatabase
import com.playrix.gar.some_services.collectors.request.MyPhotosApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LookupViewModel @Inject constructor(
    private val myPhotosApiImpl: MyPhotosApiImpl
) : ViewModel() {

    private val _lookupFlow = MutableStateFlow<LookupImageResponse?>(null)
    val lookupFlow = _lookupFlow.asStateFlow()

    fun getApiWallpaper(userRequest: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(800)
            _lookupFlow.emit(
                myPhotosApiImpl.requestPhotos(
                    userQuery = userRequest
                )
            )
        }
    }

    fun Bitmap.addToLiked(database: PhotosDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            database.likedPhotosDao()
                .insertValue(LikedPhotos(photoBitmap = this@addToLiked, time = getTime()))
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime(): String = try {
        val simpleDate = SimpleDateFormat("dd/M hh:mm:ss")
        val currentDate = simpleDate.format(Date())

        currentDate
    } catch (_: Exception) {
        ""
    }
}