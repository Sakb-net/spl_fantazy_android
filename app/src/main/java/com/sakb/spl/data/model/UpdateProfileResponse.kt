package com.sakb.spl.data.model

data class UpdateProfileResponse(
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
        var gender: Any? = Any(),
        var image: String? = "",
        var phone: String? = "",
        var state: String? = ""
    )
}