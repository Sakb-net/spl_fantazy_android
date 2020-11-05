package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class ArrayPlayerRequest(

    @field:SerializedName("substituteplayer_id")
    val substituteplayerId: String? = null,

    @field:SerializedName("substituteplayer_cost")
    val substituteplayerCost: String? = null,

    @field:SerializedName("newplayer_id")
    val newplayerId: String? = null,
)
