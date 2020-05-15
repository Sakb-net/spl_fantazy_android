package com.sakb.spl.data.model

data class PlayerResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: Data? = Data()
) {
    data class Data(
        var cost: Double? = 0.0,
        var image: String? = "",
        var link: String? = "",
        var location_player: String? = "",
        var name: String? = "",
        var point: Double? = 0.0,
        var state_player: String? = "",
        var team: String? = "",
        var type_player: String? = ""
    )
}