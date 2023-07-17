package com.playrix.gar.view.adapters_rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.ListAdapter
import com.playrix.gar.data.room.entity.LikedPhotos
import com.playrix.gar.databinding.RecyclerLikedBinding
import com.playrix.gar.view.adapters_rv.viewholder.LikedViewHolder
import com.playrix.gar.some_services.helpers.diffs.LikedDiffUtil
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LikedAdapter @Inject constructor(
    @ActivityContext private val aContext: Context
) : ListAdapter<LikedPhotos, LikedViewHolder>(
    LikedDiffUtil()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikedViewHolder {
        return LikedViewHolder(
            RecyclerLikedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), aContext
        )
    }

    override fun onBindViewHolder(holder: LikedViewHolder, position: Int) {
        val data = getItem(position)

        holder.apply {
            dateText.text = data.time
            likedImage.background = data.photoBitmap.toDrawable(aContext.resources)
            roomPojo = data
        }
    }
}