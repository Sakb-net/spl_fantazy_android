package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class FollowTeamRequest(
	var name: String = "",

	@field:SerializedName("is_email")
	var isEmail: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("is_notif")
	var isNotif: Int? = null,
)
