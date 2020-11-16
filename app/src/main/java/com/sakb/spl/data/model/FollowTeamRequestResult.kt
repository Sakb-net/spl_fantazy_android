package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class FollowTeamRequestResult(

	@field:SerializedName("is_email")
	val isEmail: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("is_notif")
	val isNotif: Int? = null,
)
