package com.sakb.spl.data.model

data class ChangePasswordResponse(
    val Message: String? = "",
    val StatusCode: Int? = 0,
    val `data`: Data? = Data()
) {
    data class Data(
        val access_token: String? = "",
        val address: String? = "",
        val city: String? = "",
        val display_name: String? = "",
        val email: String? = "",
        val gender: Any? = Any(),
        val image: String? = "",
        val image_best_team: String? = "",
        val phone: String? = "",
        val state: String? = "",
        val team_link: String? = "",
        val team_name: String? = ""
    )
}