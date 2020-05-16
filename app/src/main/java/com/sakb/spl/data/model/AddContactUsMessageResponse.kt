package com.sakb.spl.data.model

data class AddContactUsMessageResponse(
    val Message: String? = "",
    val StatusCode: Int? = 0,
    val `data`: Any? = Any(),
    val state_add: Int? = 0
)