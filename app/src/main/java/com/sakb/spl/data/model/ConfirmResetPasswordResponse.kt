package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class ConfirmResetPasswordResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null
)
