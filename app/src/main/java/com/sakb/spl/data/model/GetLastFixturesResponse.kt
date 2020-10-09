package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class GetLastFixturesResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class DataItem(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("num_week")
	val numWeek: Int? = null,

	@field:SerializedName("match_group")
	val matchGroup: List<MatchGroupItem?>? = null,

	@field:SerializedName("lang_num_week")
	val langNumWeek: String? = null,

	@field:SerializedName("start_date_day")
	val startDateDay: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,

	@field:SerializedName("end_date_day")
	val endDateDay: String? = null
)

data class MatchGroupItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("name_first")
	val nameFirst: String? = null,

	@field:SerializedName("image_second")
	val imageSecond: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id_second")
	val idSecond: Int? = null,

	@field:SerializedName("date_day")
	val dateDay: String? = null,

	@field:SerializedName("first_team_players_bonus")
	val firstTeamPlayersBonus: List<Any?>? = null,

	@field:SerializedName("first_goon")
	val firstGoon: Int? = null,

	@field:SerializedName("link_second")
	val linkSecond: String? = null,

	@field:SerializedName("link_match")
	val linkMatch: String? = null,

	@field:SerializedName("name_second")
	val nameSecond: String? = null,

	@field:SerializedName("second_team_players_bonus")
	val secondTeamPlayersBonus: List<Any?>? = null,

	@field:SerializedName("image_first")
	val imageFirst: String? = null,

	@field:SerializedName("id_first")
	val idFirst: Int? = null,

	@field:SerializedName("first_team_players")
	val firstTeamPlayers: List<Any?>? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("second_team_players")
	val secondTeamPlayers: List<Any?>? = null,

	@field:SerializedName("second_goon")
	val secondGoon: Int? = null,

	@field:SerializedName("link_first")
	val linkFirst: String? = null
)
