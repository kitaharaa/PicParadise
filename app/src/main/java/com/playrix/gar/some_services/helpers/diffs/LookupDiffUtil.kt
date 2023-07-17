package com.playrix.gar.some_services.helpers.diffs

import androidx.recyclerview.widget.DiffUtil
import com.playrix.gar.data.api.data.lists.Photos
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis

@ObfustringThis
class LookupDiffUtil : DiffUtil.ItemCallback<Photos>() {
    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean {
        return newItem == oldItem
    }

    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
        return newItem.id == oldItem.id
    }
}