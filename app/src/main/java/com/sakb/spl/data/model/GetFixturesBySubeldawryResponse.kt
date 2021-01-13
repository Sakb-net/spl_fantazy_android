package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetFixturesBySubeldawryResponse(

	@field:SerializedName("data")
	val data: List<DataItemSubFix?>? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
):Parcelable

@Parcelize
data class TeamItem(

	@field:SerializedName("player_link")
	val playerLink: String? = null,

	@field:SerializedName("player_name")
	val playerName: String? = null,

	@field:SerializedName("value")
	val value: Int? = null
):Parcelable

@Parcelize
data class DataItemSubFix(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("name_first")
	val nameFirst: String? = null,

	@field:SerializedName("image_second")
	val imageSecond: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id_second")
	val idSecond: String? = null,

	@field:SerializedName("date_day")
	val dateDay: String? = null,

	@field:SerializedName("first_goon")
	val firstGoon: String? = null,

	@field:SerializedName("link_second")
	val linkSecond: String? = null,

	@field:SerializedName("name_second")
	val nameSecond: String? = null,

	@field:SerializedName("image_first")
	val imageFirst: String? = null,

	@field:SerializedName("id_first")
	val idFirst: String? = null,

	@field:SerializedName("first_team_players")
	val firstTeamPlayers: List<TeamItem?>? = null,

	@field:SerializedName("details")
	val details: Details? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("second_team_players")
	val secondTeamPlayers: List<TeamItem?>? = null,

	@field:SerializedName("second_goon")
	val secondGoon: String? = null,

	@field:SerializedName("link_first")
	val linkFirst: String? = null
):Parcelable

@Parcelize
data class YellowCard(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

@Parcelize
data class Saves(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

@Parcelize
data class Goals(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

@Parcelize
data class RedCard(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

@Parcelize
data class GoalsConceded(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

@Parcelize
data class Bouns(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

@Parcelize
data class Details(

	@field:SerializedName("redCard")
	val redCard: RedCard? = null,

	@field:SerializedName("saves")
	val saves: Saves? = null,

	@field:SerializedName("bouns")
	val bouns: Bouns? = null,

	@field:SerializedName("goalsConceded")
	val goalsConceded: GoalsConceded? = null,

	@field:SerializedName("yellowCard")
	val yellowCard: YellowCard? = null,

	@field:SerializedName("goalAssist")
	val goalAssist: GoalAssist? = null,

	@field:SerializedName("goals")
	val goals: Goals? = null
):Parcelable

@Parcelize
data class GoalAssist(

	@field:SerializedName("first_team")
	val firstTeam: List<TeamItem?>? = null,

	@field:SerializedName("second_team")
	val secondTeam: List<TeamItem?>? = null
):Parcelable

