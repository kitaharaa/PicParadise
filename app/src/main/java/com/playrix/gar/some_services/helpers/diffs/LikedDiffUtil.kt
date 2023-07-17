package com.playrix.gar.some_services.helpers.diffs

import androidx.recyclerview.widget.DiffUtil
import com.playrix.gar.data.room.entity.LikedPhotos
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis

@ObfustringThis
class LikedDiffUtil: DiffUtil.ItemCallback<LikedPhotos>() {
    override fun areItemsTheSame(oldItem: LikedPhotos, newItem: LikedPhotos): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: LikedPhotos, newItem: LikedPhotos): Boolean {
        return oldItem == newItem
    }
}