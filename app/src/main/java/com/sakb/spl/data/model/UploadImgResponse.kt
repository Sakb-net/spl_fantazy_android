package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class UploadImgResponse(
    @SerializedName("data")
    val `data`: String? = "",
    @SerializedName("Message")
    val message: String? = "",
    @SerializedName("StatusCode")
    val statusCode: Int
)