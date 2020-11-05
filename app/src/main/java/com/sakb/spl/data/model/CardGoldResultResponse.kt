package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class CardGoldResultResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataCardGoldResult? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null,
)

data class DataCardGoldResult(

	@field:SerializedName("back_color")
	val backColor: String? = null,

	@field:SerializedName("reson_description")
	val resonDescription: String? = null,

	@field:SerializedName("mesage_pay")
	val mesagePay: String? = null,

	@field:SerializedName("ok_pay")
	val okPay: Int? = null,
)
