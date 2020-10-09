package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class StatisticsPlayerResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class AllDataItem(

	@field:SerializedName("week")
	val week: Any? = null,

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
)

data class Data(

	@field:SerializedName("all_data")
	val allData: List<AllDataItem?>? = null,

	@field:SerializedName("count_pag")
	val countPag: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null
)
