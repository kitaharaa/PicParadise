package com.playrix.gar.data.api.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.playrix.gar.data.api.lists.Photos

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