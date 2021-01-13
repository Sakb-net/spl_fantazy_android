package com.sakb.spl.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: List<Data?>? = listOf()
) : Parcelable {
    @Parcelize
    data class Data(
        var content: String? = "",
        var created_at: String? = "",
        var date: String? = "",
        var description: String? = "",
        var image: String? = "",
        var link: String? = "",
        var name: String? = "",
        var tags: List<String?>? = listOf(),
        var user_image: String? = "",
        var user_name: String? = ""
    ) : Parcelable
}
