package com.playrix.gar.some_services.helpers.diffs

import androidx.recyclerview.widget.DiffUtil
import com.playrix.gar.data.api.lists.Photos

class LookupDiffUtil : DiffUtil.ItemCallback<Photos>() {
    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean {
        return newItem == oldItem
    }

    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
        return newItem.id == oldItem.id
    }
}