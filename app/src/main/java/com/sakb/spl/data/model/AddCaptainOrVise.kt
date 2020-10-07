package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class AddCaptainOrVise(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("Message")
    val message: String? = "",
    @SerializedName("StatusCode")
    val statusCode: Int? = 0
) {
    data class Data(
        @SerializedName("msg_add")
        val msgAdd: String? = "",
        @SerializedName("ok_update")
        val okUpdate: Int? = 0
    )
}