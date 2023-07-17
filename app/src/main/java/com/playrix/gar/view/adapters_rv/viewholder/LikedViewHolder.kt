package com.playrix.gar.view.adapters_rv.viewholder

import android.content.Context
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.playrix.gar.R
import com.playrix.gar.data.constants.lookup_items.RemoveMenuPojo.chosenPojo
import com.playrix.gar.data.room.entity.LikedPhotos
import com.playrix.gar.databinding.RecyclerLikedBinding

class LikedViewHolder(binding: RecyclerLikedBinding, private val aContext: Context) :
    RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
    val likedImage = binding.imageShapeable
    val dateText = binding.tvDate

    var roomPojo: LikedPhotos? = null

    init {
        binding.root.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, meniView: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        chosenPojo = roomPojo
        menu?.setHeaderTitle("Chose move")
        val inflater: MenuInflater = (aContext as AppCompatActivity).menuInflater
        inflater.inflate(R.menu.liked_context_menu, menu)
    }
}