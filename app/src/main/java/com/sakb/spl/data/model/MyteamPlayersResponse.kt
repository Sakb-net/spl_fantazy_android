package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName


data class MyteamPlayersResponse(
    @SerializedName("data")
    var `data`: List<List<Player>>?,
    @SerializedName("image_best_team")
    var imageBestTeam: String?,
    @SerializedName("lineup")
    var lineup: List<Lineup?>?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("StatusCode")
    var statusCode: Int?
) {

    data class Lineup(
        @SerializedName("value")
        var value: Int?
    )


    data class Player(
        var cost_player: Double? = 0.0,
        var created_at: String? = "",
        var currency: String? = "",
        var eldwry_link: String? = "",
        var eldwry_name: String? = "",
        var empty_class: String? = "",
        var found_player: Int? = 0,
        var image_player: String? = "",
        var link_player: String? = "",
        var location_key_player: String? = "",
        var location_player: String? = "",
        var name_player: String? = "",
        var point_player: Int? = 0,
        var public_cla: String? = "",
        var state_player: String? = "",
        var team: String? = "",
        var type_coatch: String? = "",
        var type_key_coatch: String? = "",
        var type_loc_player: String? = "",
        var type_player: String? = "",
        var alPha: Float = 1.0f,
        var isActiveToSwap: Boolean = false,
        var isSelected: Boolean = false
    )

}

