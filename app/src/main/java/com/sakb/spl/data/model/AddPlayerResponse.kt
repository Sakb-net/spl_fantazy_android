package com.sakb.spl.data.model

data class AddPlayerResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: Data? = Data(),
    var players: List<List<PlayerMasterResponse.Data>?>? = listOf()
    ) {
    data class Data(
        var add_player: Int? = 0,
        var msg_add: String? = "",
        var player_data: PlayerData? = PlayerData(),
        var remide_sum_cost: Double? = 0.0,
        var total_team_play: Int? = 0,
        var pay_total_cost: Double? = 0.0,
        var val_player: String? = ""
    )

    data class PlayerData(
        var cost: Double? = 0.0,
        var created_at: String? = "",
        var id: Int? = 0,
        var image: String? = "",
        var is_active: Int? = 0,
        var link: String? = "",
        var location_id: Int? = 0,
        var name: String? = "",
        var num_t_shirt: Int? = 0,
        var team_id: Int? = 0,
        var type_id: Int? = 0,
        var update_by: Int? = 0,
        var updated_at: String? = ""
    )
}