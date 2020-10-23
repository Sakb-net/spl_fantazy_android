package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class GetPointSubeldawryResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataWeek? = null,

	@field:SerializedName("player_master")
	val playerMaster: List<List<PlayerMasterItemItem>>? = null,

	@field:SerializedName("lineup")
	val lineup: List<LineupItem?>? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class PlayerMasterItemItem(

	@field:SerializedName("eldwry_name")
	val eldwryName: String? = null,

	@field:SerializedName("location_key_player")
	val locationKeyPlayer: String? = null,

	@field:SerializedName("state_player")
	val statePlayer: String? = null,

	@field:SerializedName("total_points")
	val totalPoints: Int? = null,

	@field:SerializedName("link_player")
	val linkPlayer: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("public_cla")
	val publicCla: String? = null,

	@field:SerializedName("type_player")
	val typePlayer: String? = null,

	@field:SerializedName("player_id")
	val playerId: Int? = null,

	@field:SerializedName("name_player")
	val namePlayer: String? = null,

	@field:SerializedName("fix")
	val fix: String? = null,

	@field:SerializedName("type_loc_player")
	val typeLocPlayer: String? = null,

	@field:SerializedName("type_key_coatch")
	val typeKeyCoatch: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("image_player")
	val imagePlayer: String? = null,

	@field:SerializedName("match_link")
	val matchLink: String? = null,

	@field:SerializedName("type_coatch")
	val typeCoatch: String? = null,

	@field:SerializedName("cost_game_player")
	val costGamePlayer: Int? = null,

	@field:SerializedName("teamCode")
	val teamCode: String? = null,

	@field:SerializedName("team")
	val team: String? = null,

	@field:SerializedName("cost_player")
	val costPlayer: Int? = null,

	@field:SerializedName("point_player")
	val pointPlayer: Int? = null,

	@field:SerializedName("match_name")
	val matchName: String? = null,

	@field:SerializedName("sell_cost")
	val sellCost: Int? = null,

	@field:SerializedName("found_player")
	val foundPlayer: Int? = null,

	@field:SerializedName("eldwry_link")
	val eldwryLink: String? = null,

	@field:SerializedName("form")
	val form: Int? = null,

	@field:SerializedName("buy_cost")
	val buyCost: Int? = null,

	@field:SerializedName("location_player")
	val locationPlayer: String? = null,

	@field:SerializedName("myteam_order_id")
	val myteamOrderId: Int? = null
)

data class DataWeek(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("num_week")
	val numWeek: Int? = null,

	@field:SerializedName("heigh_point")
	val heighPoint: Int? = null,

	@field:SerializedName("sort_gwla")
	val sortGwla: Int? = null,

	@field:SerializedName("final_point")
	val finalPoint: Int? = null,

	@field:SerializedName("transfer")
	val transfer: Int? = null,

	@field:SerializedName("avg_point")
	val avgPoint: Int? = null,

	@field:SerializedName("lang_num_week")
	val langNumWeek: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)

data class LineupItem(

	@field:SerializedName("value")
	val value: Int? = null
)
