package com.sakb.spl.data.model

data class ContactUsResponse(
    val Message: String? = "",
    val StatusCode: Int? = 0,
    val `data`: Data? = Data()
) {
    data class Data(
        val address: String? = "",
        val content: Any? = Any(),
        val email: String? = "",
        val lat: String? = "",
        val long: String? = "",
        val phone: String? = "",
        val title: String? = ""
    )
}