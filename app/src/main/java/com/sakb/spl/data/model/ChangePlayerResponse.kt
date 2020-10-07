package com.sakb.spl.data.model

data class ChangePlayerResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: List<List<PlayerMasterResponse.Data>?>? = listOf(),
    var image_best_team: String? = "",
    var lineup: List<Any?>? = listOf(),
    var pay_total_cost: Double? = 0.0,
    var remide_sum_cost: Double? = 0.0,
    var result_change: ResultChange? = ResultChange(),
    var total_cost_play: Int? = 0,
    var total_team_play: Int? = 0
) {
    data class ResultChange(
        var delete_player: Int? = 0,
        var msg_delete: String? = ""
    )
}