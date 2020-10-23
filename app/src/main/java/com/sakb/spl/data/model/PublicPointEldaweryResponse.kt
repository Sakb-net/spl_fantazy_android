package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class PublicPointEldaweryResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataPublic? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class DataPublic(

	@field:SerializedName("game_week_changes")
	val gameWeekChanges: Int? = null,

	@field:SerializedName("sum_total_subeldwry")
	val sumTotalSubeldwry: Int? = null,

	@field:SerializedName("sum_total_points")
	val sumTotalPoints: Int? = null,

	@field:SerializedName("count_free_weekgamesubstitute")
	val countFreeWeekgamesubstitute: Int? = null,

	@field:SerializedName("sort_final_users")
	val sortFinalUsers: Int? = null,

	@field:SerializedName("total_changes")
	val totalChanges: Int? = null,

	@field:SerializedName("count_total_users")
	val countTotalUsers: Int? = null
)
