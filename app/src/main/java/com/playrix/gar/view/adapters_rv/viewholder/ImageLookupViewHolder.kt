package com.playrix.gar.view.adapters_rv.viewholder

import android.content.Context
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.playrix.gar.R
import com.playrix.gar.data.constants.lookup_items.ImageUrlObject.imageUrl
import com.playrix.gar.databinding.RecyclerLookupBinding

class ImageLookupViewHolder(rvBinding: RecyclerLookupBinding, private val aContext: Context) :
    RecyclerView.ViewHolder(rvBinding.root), View.OnCreateContextMenuListener {
    val wallpaperShapeableImage = rvBinding.imageShapeable
    val rootView = rvBinding.root

    var imageFromAPi: String? = null

    init {
        rootView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, meniView: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        imageUrl = imageFromAPi

        menu.manageMenuSettings()
    }

    private fun ContextMenu?.manageMenuSettings() {
        if (this != null) {
            setHeaderTitle("Chose action")
            val inflater: MenuInflater = (aContext as AppCompatActivity).menuInflater
            inflater.inflate(R.menu.search_context_menu, this)
        }
    }
}