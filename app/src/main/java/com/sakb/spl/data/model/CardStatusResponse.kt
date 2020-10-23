package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class CardStatusResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataCardStatus? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class DataCardStatus(

	@field:SerializedName("bench_card")
	val benchCard: Int? = null,

	@field:SerializedName("triple_card")
	val tripleCard: Int? = null
)
