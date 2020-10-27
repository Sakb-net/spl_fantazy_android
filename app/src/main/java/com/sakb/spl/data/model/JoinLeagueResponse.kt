package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class JoinLeagueResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataJoinLeague? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class DataJoinLeague(

	@field:SerializedName("status")
	val status: Int? = null
)
