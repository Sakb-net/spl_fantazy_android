package com.sakb.spl.data.model
import com.google.gson.annotations.SerializedName


/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

data class TermsResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("Message")
    val message: String? = null,
    @SerializedName("StatusCode")
    val statusCode: Int? = null
) {
    data class Data(
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("title_content")
        val titleContent: String? = null
    )
}