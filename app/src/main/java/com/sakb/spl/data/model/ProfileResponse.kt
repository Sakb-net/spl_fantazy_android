package com.sakb.spl.data.model
import com.google.gson.annotations.SerializedName


data class ProfileResponse(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("Message")
    val message: String? = "",
    @SerializedName("StatusCode")
    val statusCode: Int? = 0
) {
    data class Data(
        @SerializedName("access_token")
        val accessToken: String? = "",
        @SerializedName("address")
        val address: String? = "",
        @SerializedName("city")
        val city: String? = "",
        @SerializedName("display_name")
        val displayName: String? = "",
        @SerializedName("email")
        val email: String? = "",
        @SerializedName("gender")
        val gender: Any? = Any(),
        @SerializedName("image")
        val image: String? = "",
        @SerializedName("image_best_team")
        val imageBestTeam: String? = "",
        @SerializedName("phone")
        val phone: String? = "",
        @SerializedName("state")
        val state: Any? = Any(),
        @SerializedName("team_link")
        val teamLink: String? = "",
        @SerializedName("team_name")
        val teamName: String? = ""
    )
}