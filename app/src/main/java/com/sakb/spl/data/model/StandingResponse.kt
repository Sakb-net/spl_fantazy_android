package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StandingResponse(

    @field:SerializedName("Message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: DataStanding? = null,

    @field:SerializedName("StatusCode")
    val statusCode: Int? = null,
) : Parcelable

@Parcelize
data class GroupEldwryStanding(

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
    val creatorId: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("subeldwry_link")
    val subeldwryLink: String? = null,
) : Parcelable

@Parcelize
data class DataStanding(

    @field:SerializedName("owner")
    val owner: Int? = null,

    @field:SerializedName("users_group")
    val usersGroup: List<UsersGroupItem?>? = null,

    @field:SerializedName("group_eldwry")
    val groupEldwry: GroupEldwryStanding? = null,

    @field:SerializedName("matches_group")
    val matchGroupList: List<MatchGroupGroupItem?>? = null,
) : Parcelable

@Parcelize
data class SubeldwrysItem(

    @field:SerializedName("end_date")
    val endDate: String? = null,

    @field:SerializedName("num_week")
    val numWeek: String? = null,

    @field:SerializedName("lang_num_week")
    val langNumWeek: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("start_date_day")
    val startDateDay: String? = null,

    @field:SerializedName("start_date")
    val startDate: String? = null,

    @field:SerializedName("end_date_day")
    val endDateDay: String? = null,
) : Parcelable

@Parcelize
data class UsersGroupItem(

    @field:SerializedName("num_week")
    val numWeek: String? = null,

    @field:SerializedName("user_email")
    val userEmail: String? = null,

    @field:SerializedName("gw_points")
    val gwPoints: String? = null,

    @field:SerializedName("name_group")
    val nameGroup: String? = null,

    @field:SerializedName("user_name")
    val userName: String? = null,

    @field:SerializedName("total_points")
    val totalPoints: String? = null,

    @field:SerializedName("lang_num_week")
    val langNumWeek: String? = null,

    @field:SerializedName("subeldwry_link")
    val subeldwryLink: String? = null,

    @field:SerializedName("sort")
    val sort: String? = null,

    @field:SerializedName("display_name")
    val displayName: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("name_team")
    val nameTeam: String? = null,

    @field:SerializedName("link_group")
    val linkGroup: String? = null,
) : Parcelable

@Parcelize
data class MatchGroupGroupItem(
    @field:SerializedName("first_team_name")
    val firstTeamName: String? = null,

    @field:SerializedName("first_user_name")
    val firstUserName:String? = null,

    @field:SerializedName("first_team_points")
    val firstTeamPoints:String? = null,

    @field:SerializedName("first_team_bouns")
    val firstTeamBouns:String? = null,

    @field:SerializedName("second_team_name")
    val secondTeamName:String? = null,

    @field:SerializedName("second_user_name")
    val secondUserName:String? = null,

    @field:SerializedName("second_team_points")
    val secondTeamPoints:String? = null,

    @field:SerializedName("second_team_bouns")
    val secondTeamBouns:String? = null,

    @field:SerializedName("sort")
    val sort: String? = null,

    @field:SerializedName("name_group")
    val nameGroup: String? = null,

    @field:SerializedName("link_group")
    val linkGroup: String? = null,

    @field:SerializedName("num_week")
    val numWeek: String? = null,

    @field:SerializedName("lang_num_week")
    val langNumWeek: String? = null,

    @field:SerializedName("subeldwry_link")
    val subeldwryLink: String? = null
) : Parcelable
