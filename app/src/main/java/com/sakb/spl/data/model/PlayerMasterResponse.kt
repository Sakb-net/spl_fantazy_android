package com.sakb.spl.data.model

data class PlayerMasterResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: List<List<Data>?>? = listOf(),
    var image_best_team: String? = "",
    var lineup: List<Any?>? = listOf(),
    var pay_total_cost: Double? = 0.0,
    var remide_sum_cost: Double? = 0.0,
    var total_cost_play: Int? = 0,
    var total_team_play: Int? = 0
) {
    data class Data(
        var player_id: Int? = 0,
        var cost_player: Double? = 0.0,
        var created_at: String? = "",
        var currency: String? = "",
        var eldwry_link: String? = "",
        var eldwry_name: String? = "",
        var empty_class: String? = "",
        var found_player: Int? = 0,
        var alPha: Float = 1.0f,
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
        var type_player: String? = ""
    )
}