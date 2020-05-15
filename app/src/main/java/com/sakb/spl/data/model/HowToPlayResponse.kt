package com.sakb.spl.data.model
import com.google.gson.annotations.SerializedName


data class HowToPlayResponse(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("Message")
    val message: String? = "",
    @SerializedName("StatusCode")
    val statusCode: Int? = 0
) {
    data class Data(
        @SerializedName("content_help")
        val contentHelp: List<ContentHelp>? = listOf(),
        @SerializedName("content_page")
        val contentPage: ContentPage? = ContentPage(),
        @SerializedName("content_role")
        val contentRole: List<ContentRole>? = listOf()
    )

    data class ContentHelp(
        @SerializedName("content")
        val content: String? = "",
        @SerializedName("title")
        val title: String? = "",
        var isActivated: Boolean = false
    )

    data class ContentPage(
        @SerializedName("content")
        val content: Any? = Any(),
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("title")
        val title: String? = ""
    )

    data class ContentRole(
        @SerializedName("content")
        val content: String? = "",
        @SerializedName("title")
        val title: String? = "",
        var isActivated: Boolean? = false
    )

}