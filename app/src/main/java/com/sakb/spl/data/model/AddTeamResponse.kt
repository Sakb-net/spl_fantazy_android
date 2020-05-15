package com.sakb.spl.data.model

data class AddTeamResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: Data? = Data()
) {
    data class Data(
        var add_team: Int? = 0,
        var msg_add: String? = ""
    )
}