package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataPlayer? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
) : Parcelable

@Parcelize
data class StatisticsDataItem(

	@field:SerializedName("statistic")
	val statistic: List<StatisticItem?>? = null,

	@field:SerializedName("link_match")
	val linkMatch: String? = null,

	@field:SerializedName("againestTeamResult")
	val againestTeamResult: Int? = null,

	@field:SerializedName("week")
	val week: Int? = null,

	@field:SerializedName("againestTeam")
	val againestTeam: String? = null,

	@field:SerializedName("ownTeam")
	val ownTeam: String? = null,

	@field:SerializedName("ownTeamResult")
	val ownTeamResult: Int? = null,

	@field:SerializedName("extraPoints")
	val extraPoints: Int? = null,

	@field:SerializedName("points")
	val points: Int? = null
) : Parcelable

@Parcelize
data class StatisticItem(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("action_point")
	val actionPoint: String? = null,

	@field:SerializedName("type_key_point")
	val typeKeyPoint: String? = null,

	@field:SerializedName("lang_point")
	val langPoint: String? = null,

	@field:SerializedName("points")
	val points: Int? = null
) : Parcelable

@Parcelize
data class PlayerData(

	@field:SerializedName("week")
	val week: String? = null,

	@field:SerializedName("eldwry_name")
	val eldwryName: String? = null,

	@field:SerializedName("state_player")
	val statePlayer: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("is_choose")
	val isChoose: Int? = null,

	@field:SerializedName("type_player")
	val typePlayer: String? = null,

	@field:SerializedName("point")
	val point: Int? = null,

	@field:SerializedName("location_id")
	val locationId: Int? = null,

	@field:SerializedName("influence")
	val influence: Int? = null,

	@field:SerializedName("player_id")
	val playerId: Int? = null,

	@field:SerializedName("fix")
	val fix: String? = null,

	@field:SerializedName("ICT_index")
	val iCTIndex: Int? = null,

	@field:SerializedName("threats")
	val threats: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("creativity")
	val creativity: Int? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("cost")
	val cost: Int? = null,

	@field:SerializedName("teamCode")
	val teamCode: String? = null,

	@field:SerializedName("team")
	val team: String? = null,

	@field:SerializedName("sel_percentage")
	val selPercentage: Double? = null,

	@field:SerializedName("sell_cost")
	val sellCost: Int? = null,

	@field:SerializedName("eldwry_link")
	val eldwryLink: String? = null,

	@field:SerializedName("form")
	val form: Int? = null,

	@field:SerializedName("buy_cost")
	val buyCost: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location_player")
	val locationPlayer: String? = null
) : Parcelable

@Parcelize
data class DataPlayer(

	@field:SerializedName("player_data")
	val playerData: PlayerData? = null,

	@field:SerializedName("statistics_data")
	val statisticsData: List<StatisticsDataItem?>? = null
) : Parcelable
