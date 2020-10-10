package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */
data class LoginResponse(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("Message")
    val message: String? = null,
    @SerializedName("StatusCode")
    val statusCode: Int? = null
) {
    data class Data(
        @SerializedName("access_token")
        val accessToken: String? = null,
        @SerializedName("address")
        val address: String? = null,
        @SerializedName("city")
        val city: String? = null,
        @SerializedName("display_name")
        val displayName: String? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("gender")
        val gender: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("new_fcm_token")
        val newFcmToken: Any? = null,
        @SerializedName("old_fcm_token")
        val oldFcmToken: Any? = null,
        @SerializedName("phone")
        val phone: String? = null,
        @SerializedName("state")
        val state: String? = null,
        @SerializedName("choose_team")
        var chooseTeam: Int? = null
    )
}