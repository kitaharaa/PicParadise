package com.playrix.gar.some_services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playrix.gar.data.constants.lookup_items.RemoveMenuPojo.chosenPojo
import com.playrix.gar.data.room.entity.LikedPhotos
import com.playrix.gar.data.room.PhotosDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedViewModel @Inject constructor() : ViewModel() {
    private val _likedPhotosFlow = MutableStateFlow<List<LikedPhotos>>(emptyList())
    val likedPhotosFlow = _likedPhotosFlow.asStateFlow()

    fun extractLiked(databaseInstance: PhotosDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            pushUpdates(databaseInstance)
        }
    }

    private fun pushUpdates(databaseInstance: PhotosDatabase) {
        _likedPhotosFlow.value = databaseInstance.likedPhotosDao().getAll()
    }

    fun removeImage(databaseInstance: PhotosDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            val rp = chosenPojo

            if (rp != null) {
                databaseInstance.likedPhotosDao().delete(rp)
            }

            pushUpdates(databaseInstance)
        }
    }
}