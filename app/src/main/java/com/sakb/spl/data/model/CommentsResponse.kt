package com.sakb.spl.data.model
import com.google.gson.annotations.SerializedName


/**
 * Created by dev.mahmoud_ashraf on 10/20/2019.
 */

//

data class CommentsResponse(
    @SerializedName("count_data")
    val countData: String? = "",
    @SerializedName("data")
    val `data`: List<Data?>? = listOf(),
    @SerializedName("Message")
    val message: String? = "",
    @SerializedName("StatusCode")
    val statusCode: Int? = 0
) {
    data class Data(
        @SerializedName("audio")
        val audio: Any? = Any(),
        @SerializedName("child_comments")
        val childComments: List<ChildComment?>? = listOf(),
        @SerializedName("content")
        val content: String? = "",
        @SerializedName("created_at")
        val createdAt: CreatedAtX? = CreatedAtX(),
        @SerializedName("date")
        val date: String? = "",
        @SerializedName("image")
        val image: Any? = Any(),
        @SerializedName("like")
        val like: Boolean? = false,
        @SerializedName("link")
        val link: String? = "",
        @SerializedName("num_like")
        val numLike: String? = "",
        @SerializedName("owner_data")
        val ownerData: String? = "",
        @SerializedName("parent_id")
        val parentId: Int? = 0,
        @SerializedName("parent_user_image")
        val parentUserImage: String? = "",
        @SerializedName("parent_user_name")
        val parentUserName: String? = "",
        @SerializedName("star_rate")
        val starRate: Int? = 0,
        @SerializedName("video")
        val video: Any? = Any()
    )

    data class ChildComment(
        @SerializedName("audio")
        val audio: Any? = Any(),
        @SerializedName("content")
        val content: String? = "",
        @SerializedName("created_at")
        val createdAt: CreatedAt? = CreatedAt(),
        @SerializedName("date")
        val date: String? = "",
        @SerializedName("image")
        val image: Any? = Any(),
        @SerializedName("like")
        val like: Boolean? = false,
        @SerializedName("link")
        val link: String? = "",
        @SerializedName("num_like")
        val numLike: String? = "",
        @SerializedName("owner_data")
        val ownerData: String? = "",
        @SerializedName("parent_id")
        val parentId: Int? = 0,
        @SerializedName("parent_user_image")
        val parentUserImage: String? = "",
        @SerializedName("parent_user_name")
        val parentUserName: String? = "",
        @SerializedName("replay_id")
        val replayId: Int? = 0,
        @SerializedName("replay_user")
        val replayUser: Any? = Any(),
        @SerializedName("replay_user_image")
        val replayUserImage: String? = "",
        @SerializedName("replay_user_name")
        val replayUserName: String? = "",
        @SerializedName("star_rate")
        val starRate: Int? = 0,
        @SerializedName("video")
        val video: Any? = Any()
    )

    data class CreatedAt(
        @SerializedName("date")
        val date: String? = "",
        @SerializedName("timezone")
        val timezone: String? = "",
        @SerializedName("timezone_type")
        val timezoneType: Int? = 0
    )

    data class CreatedAtX(
        @SerializedName("date")
        val date: String? = "",
        @SerializedName("timezone")
        val timezone: String? = "",
        @SerializedName("timezone_type")
        val timezoneType: Int? = 0
    )
}