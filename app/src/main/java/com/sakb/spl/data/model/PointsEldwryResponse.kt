package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class PointsEldwryResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: List<DataItemPoints?>? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class DataItemPoints(

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
