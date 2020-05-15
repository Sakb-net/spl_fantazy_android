package com.sakb.spl.data.model
import com.google.gson.annotations.SerializedName


data class AddDirectInsideChange(
    @SerializedName("data")
    val `data`: List<List<MyteamPlayersResponse.Player>>? = listOf(),
    @SerializedName("image_best_team")
    val imageBestTeam: String? = "",
    @SerializedName("lineup")
    val lineup: List<Lineup?>? = listOf(),
    @SerializedName("Message")
    val message: String? = "",
    @SerializedName("msg_add")
    val msgAdd: String? = "",
    @SerializedName("ok_add")
    val okAdd: Int? = 0,
    @SerializedName("StatusCode")
    val statusCode: Int? = 0
) {
    data class Lineup(
        @SerializedName("value")
        val value: Int? = 0
    )
}