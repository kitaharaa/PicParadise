package com.playrix.gar.data.api.data.lists

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis

@ObfustringThis
@Keep
data class Photos(
    @SerializedName("id")
    val id: Int,
    @SerializedName("width")
    val photo_width: Int,
    @SerializedName("height")
    val photo_height: Int,
    @SerializedName("url")
    val photo_url: String,
    @SerializedName("photographer")
    val photo_photographer: String,
    @SerializedName("photographer_url")
    val photo_photographer_url: String,
    @SerializedName("photographer_id")
    val photo_photographer_id: Int,
    @SerializedName("avg_color")
    val photo_avg_color: String,
    @SerializedName("src")
    val photo_src: Src,
    @SerializedName("liked")
    val photo_liked: Boolean,
    @SerializedName("alt")
    val photo_alt: String
)