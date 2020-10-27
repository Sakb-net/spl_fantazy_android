package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class GetAllLeaguesResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataLeague? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class GroupEldwryItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("num_week")
	val numWeek: String? = null,

	@field:SerializedName("eldwry_name")
	val eldwryName: String? = null,

	@field:SerializedName("prev_points")
	val prevPoints: String? = null,

	@field:SerializedName("lang_num_week")
	val langNumWeek: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("subeldwry_link")
	val subeldwryLink: String? = null,

	@field:SerializedName("current_points")
	val currentPoints: String? = null,

	@field:SerializedName("current_sort")
	val currentSort: String? = null,

	@field:SerializedName("num_allow_users")
	val numAllowUsers: String? = null,

	@field:SerializedName("prev_sort")
	val prevSort: String? = null,

	@field:SerializedName("eldwry_link")
	val eldwryLink: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("creator_id")
	val creatorId: Any? = null
)

data class DataLeague(

	@field:SerializedName("group_eldwry")
	val groupEldwry: List<GroupEldwryItem?>? = null
)
