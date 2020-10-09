package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class GetAllSubeldawryResponse(

	@field:SerializedName("data")
	val data: List<DataItemSub?>? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("active_subeldwry_link")
	val activeSubeldwryLink: String? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class DataItemSub(

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
	val endDateDay: String? = null
)