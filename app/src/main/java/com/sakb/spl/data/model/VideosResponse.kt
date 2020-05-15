package com.sakb.spl.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */
@Parcelize
data class VideosResponse(
    @SerializedName("data")
    val `data`: MutableList<Data?>? = null,
    @SerializedName("Message")
    val message: String? = null,
    @SerializedName("StatusCode")
    val statusCode: Int? = null
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("created_at")
        val createdAt: String? = null,
        @SerializedName("date")
        val date: String? = null,
        @SerializedName("extension")
        val extension: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("link")
        val link: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("upload")
        val upload: Int? = null,
        @SerializedName("upload_id")
        val uploadId: String? = null,
        @SerializedName("video")
        val video: String? = null
    ) : Parcelable
}