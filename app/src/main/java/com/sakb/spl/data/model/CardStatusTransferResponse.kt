package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class CardStatusTransferResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: DataCard? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null
)

data class DataCard(

    @field:SerializedName("total_cost_play")
    val totalCostPlay: Int? = null,

    @field:SerializedName("substitutes_points")
    val substitutesPoints: Int? = null,

    @field:SerializedName("active_card")
    val activeCard: Int? = null,

    @field:SerializedName("remide_sum_cost")
    val remideSumCost: Int? = null,

    @field:SerializedName("total_team_play")
    val totalTeamPlay: Int? = null,

    @field:SerializedName("pay_total_cost")
    val payTotalCost: Int? = null
)
