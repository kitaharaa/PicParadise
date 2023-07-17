package com.playrix.gar.some_services.helpers.diffs

import androidx.recyclerview.widget.DiffUtil
import com.playrix.gar.data.room.entity.LikedPhotos

class LikedDiffUtil: DiffUtil.ItemCallback<LikedPhotos>() {
    override fun areItemsTheSame(oldItem: LikedPhotos, newItem: LikedPhotos): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: LikedPhotos, newItem: LikedPhotos): Boolean {
        return oldItem == newItem
    }
}