package com.sakb.spl.data.model

data class PlayerByTypeResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: List<Data?>? = mutableListOf()
) {
    data class Data(
        var all_type_key: String? = "",
        var color: String? = "",
        var currency: String? = "",
        var name: String? = "",
        var players_group: MutableList<PlayersGroup?>? = mutableListOf(),
        var teams: MutableList<Team?>? = mutableListOf(),
        var type_key: String? = ""
    )

    data class PlayersGroup(
        var state_player: String? = "",
        var cost: Double? = 0.0,
        var point: Double? = 0.0,
        var image: String? = "",
        var link: String? = "",
        var location_player: String? = "",
        var name: String? = "",
        var team: String? = "",
        var type_player: String? = ""
    )

    data class Team(
        var link: String? = "",
        var team: String? = ""
    )
}
/*

    data class PlayersGroup(
        var cost: Double? = 0.0,
        var point: Double? = 0.0,
        var image: String? = "",
        var link: String? = "",
        var location_player: String? = "",
        var name: String? = "",
        var team: String? = "",
        var type_player: String? = ""
    )
}
*/
