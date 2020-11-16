package com.sakb.spl.data.model

data class SocialResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: Data? = Data()
) {
    data class Data(
        var access_token: String? = "",
        var address: String? = "",
        var city: String? = "",
        var display_name: String? = "",
        var email: String? = "",
        var gender: String? = "",
        var image: String? = "",
        var new_fcm_token: String? = String(),
        var old_fcm_token: String? = String(),
        var phone: String? = "",
        var state: String? = "",
        var id: Int? = 0,
        var choose_team: Int? = 0,
        var image_best_team: String? = "",
        var team_name: String? = "",
        var team_link: String? = "",
    )
}