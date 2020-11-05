package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class CardGoldInfoResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataCardGold? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null,
)

data class DataCardGold(

	@field:SerializedName("shopperResultUrl")
	val shopperResultUrl: String? = null,

	@field:SerializedName("checkoutId")
	val checkoutId: String? = null,
)
