package com.sakb.spl.data.model

data class GetTeamResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: MutableList<Data?>? = mutableListOf()
) {
    data class Data(
        var link: String? = "",
        var name: String? = ""
    )
}