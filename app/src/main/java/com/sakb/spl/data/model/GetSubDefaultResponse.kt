package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class GetSubDefaultResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: DataSubDef? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null,
)

data class DataSubDef(

    @field:SerializedName("substitute")
    val substitute: String? = null,

    @field:SerializedName("msg_add")
    val msgAdd: String? = null,
)
