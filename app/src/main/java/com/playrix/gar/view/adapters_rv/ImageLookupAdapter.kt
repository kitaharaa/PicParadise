package com.playrix.gar.view.adapters_rv

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.playrix.gar.R
import com.playrix.gar.data.api.lists.Photos
import com.playrix.gar.data.constants.lookup_items.PhonePageConstants.HOME_PAGE
import com.playrix.gar.data.constants.lookup_items.PhonePageConstants.PRIVACY_PAGE
import com.playrix.gar.databinding.RecyclerLookupBinding
import com.playrix.gar.some_services.helpers.diffs.LookupDiffUtil
import com.playrix.gar.some_services.services.lookuper.LookupImageManager
import com.playrix.gar.view.adapters_rv.viewholder.ImageLookupViewHolder
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ImageLookupAdapter @Inject constructor(
    @ActivityContext private val aContext: Context,
    private val wallpaperManager: LookupImageManager
) : ListAdapter<Photos, ImageLookupViewHolder>(
    LookupDiffUtil()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ImageLookupViewHolder {
        return ImageLookupViewHolder(
            RecyclerLookupBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), aContext
        )
    }

    override fun onBindViewHolder(holder: ImageLookupViewHolder, position: Int) {
        val imageUrl = getItem(position).photo_src.psource_portrait
        holder.imageFromAPi = imageUrl

        holder.rootView.setOnClickListener {
            imageUrl.showMyDialog()
        }

        aContext.setImageIntoView(imageUrl, holder.wallpaperShapeableImage)
    }

    private fun String.showMyDialog() {
        Dialog(aContext).apply {
            setContentView(R.layout.fragment_functionality)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val imageView = this.findViewById<ShapeableImageView>(R.id.image_shapeable)

            aContext.setImageIntoView(this@showMyDialog, imageView)

            addViewListeners(imageView)
            show()
        }
    }

    private fun Context.setImageIntoView(url: String, imageVIew: ShapeableImageView) {
        Glide.with(this).load(url).centerCrop().into(imageVIew)
    }

    private fun Dialog.addViewListeners(view: ShapeableImageView) {
        findViewById<TextView>(R.id.button_home).setOnClickListener {
            wallpaperManager.setImageAsWallpaper(HOME_PAGE, aContext, view)
        }

        findViewById<TextView>(R.id.button_lock).setOnClickListener {
            wallpaperManager.setImageAsWallpaper(PRIVACY_PAGE, aContext, view)
        }
    }
}