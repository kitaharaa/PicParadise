package com.playrix.gar.data.api.data.lists

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis

@ObfustringThis
@Keep
data class Src(
    @SerializedName("original")
    val source_original: String,
    @SerializedName("large2x")
    val source_large2x: String,
    @SerializedName("large")
    val source_large: String,
    @SerializedName("medium")
    val source_medium: String,
    @SerializedName("small")
    val source_small: String,
    @SerializedName("portrait")
    val psource_portrait: String,
    @SerializedName("landscape")
    val source_landscape: String,
    @SerializedName("tiny")
    val source_tiny: String
)