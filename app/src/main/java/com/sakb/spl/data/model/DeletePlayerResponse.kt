package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class DeletePlayerResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: DataDelete? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null,
)

data class DataDelete(

    @field:SerializedName("update")
    val update: Boolean? = null,

    @field:SerializedName("users_group")
    val usersGroup: List<UsersGroupItemSetting?>? = null,
)
