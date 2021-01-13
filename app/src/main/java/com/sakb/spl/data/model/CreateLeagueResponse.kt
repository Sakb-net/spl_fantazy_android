package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateLeagueResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: DataCreateLeague? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null
) : Parcelable

@Parcelize
data class GroupEldwryCreate(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("is_active")
    val isActive: Int? = null,

    @field:SerializedName("eldwry_id")
    val eldwryId: Int? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("num_allow_users")
    val numAllowUsers: Int? = null,

    @field:SerializedName("lang_name")
    val langName: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("creator_id")
    val creatorId: Int? = null,

    @field:SerializedName("start_sub_eldwry_id")
    val startSubEldwryId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("update_by")
    val updateBy: Int? = null,

    @field:SerializedName("game_id")
    val gameId: Int? = null,
) : Parcelable

@Parcelize
data class DataCreateLeague(

    @field:SerializedName("url_name")
    val urlName: String? = null,

    @field:SerializedName("current_url_page")
    val currentUrlPage: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("group_eldwry")
    val groupEldwry: GroupEldwryCreate? = null,

    @field:SerializedName("type_group")
    val type_group: String? = null,

    ) : Parcelable
