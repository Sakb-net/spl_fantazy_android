package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class SettingGroupsResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: DataSetting? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null,
)

data class DataSetting(

    @field:SerializedName("users_group")
    val usersGroup: List<UsersGroupItemSetting?>? = null,

    @field:SerializedName("group_eldwry")
    val groupEldwry: GroupEldwry? = null,
)

data class UsersGroupItemSetting(

    @field:SerializedName("num_week")
    val numWeek: String? = null,

    @field:SerializedName("user_email")
    val userEmail: String? = null,

    @field:SerializedName("name_group")
    val nameGroup: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("user_name")
    val userName: String? = null,

    @field:SerializedName("lang_num_week")
    val langNumWeek: String? = null,

    @field:SerializedName("subeldwry_link")
    val subeldwryLink: String? = null,

    @field:SerializedName("name_team")
    val nameTeam: String? = null,

    @field:SerializedName("display_name")
    val displayName: String? = null,

    @field:SerializedName("link_group")
    val linkGroup: String? = null,

    @field:SerializedName("points")
    val points: String? = null,
)

data class GroupEldwry(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("num_allow_users")
    val numAllowUsers: String? = null,

    @field:SerializedName("num_week")
    val numWeek: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("eldwry_link")
    val eldwryLink: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("eldwry_name")
    val eldwryName: String? = null,

    @field:SerializedName("lang_num_week")
    val langNumWeek: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("creator_id")
    val creatorId: Any? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("subeldwry_link")
    val subeldwryLink: String? = null,
)
