package com.playrix.gar.data.api.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.playrix.gar.data.api.data.lists.Photos
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis

@ObfustringThis
@Keep
data class LookupImageResponse(
    @SerializedName("total_results")
    val total_results_response: Int,
    @SerializedName("page")
    val page_response: Int,
    @SerializedName("per_page")
    val per_page_response: Int,
    @SerializedName("photos")
    val photos_response: List<Photos>,
    @SerializedName("next_page")
    val next_page_response: String
)