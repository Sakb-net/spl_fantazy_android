package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created by dev_mahmoud_ashraf on 17,02,2020
 */
data class HomeResponse(
    @SerializedName("data")
    var `data`: Data? = Data(),
    @SerializedName("Message")
    var message: String? = "",
    @SerializedName("StatusCode")
    var statusCode: Int? = 0
) {
    data class Data(
        @SerializedName("fixtures")
        var fixtures: List<Fixture?>? = listOf(),
        @SerializedName("news")
        var news: List<New?>? = listOf(),
        @SerializedName("videos")
        var videos: List<Video?>? = listOf()
    )

    data class Fixture(
        @SerializedName("end_date")
        var endDate: String? = "",
        @SerializedName("end_date_day")
        var endDateDay: String? = "",
        @SerializedName("match_group")
        var matchGroup: List<MatchGroup?>? = listOf(),
        @SerializedName("start_date")
        var startDate: String? = "",
        @SerializedName("start_date_day")
        var startDateDay: String? = ""
    )

    data class MatchGroup(
        @SerializedName("date")
        var date: String? = "",
        @SerializedName("date_day")
        var dateDay: String? = "",
        @SerializedName("description")
        var description: Any? = Any(),
        @SerializedName("first_goon")
        var firstGoon: Int? = 0,
        @SerializedName("image_first")
        var imageFirst: String? = "",
        @SerializedName("image_second")
        var imageSecond: String? = "",
        @SerializedName("link_first")
        var linkFirst: String? = "",
        @SerializedName("link_second")
        var linkSecond: String? = "",
        @SerializedName("name_first")
        var nameFirst: String? = "",
        @SerializedName("name_second")
        var nameSecond: String? = "",
        @SerializedName("second_goon")
        var secondGoon: Int? = 0,
        @SerializedName("time")
        var time: String? = ""
    )

    data class New(
        @SerializedName("content")
        var content: String? = "",
        @SerializedName("created_at")
        var createdAt: String? = "",
        @SerializedName("date")
        var date: String? = "",
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("image")
        var image: String? = "",
        @SerializedName("link")
        var link: String? = "",
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("tags")
        var tags: List<Any?>? = listOf(),
        @SerializedName("user_image")
        var userImage: String? = "",
        @SerializedName("user_name")
        var userName: String? = ""
    )

    data class Video(
        @SerializedName("content")
        var content: String? = "",
        @SerializedName("created_at")
        var createdAt: String? = "",
        @SerializedName("date")
        var date: String? = "",
        @SerializedName("extension")
        var extension: String? = "",
        @SerializedName("image")
        var image: String? = "",
        @SerializedName("link")
        var link: String? = "",
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("upload")
        var upload: Int? = 0,
        @SerializedName("upload_id")
        var uploadId: String? = "",
        @SerializedName("video")
        var video: String? = ""
    )
}