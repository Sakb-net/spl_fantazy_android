package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

data class AwardResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataAward? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
)

data class ContentPage(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: Any? = null
)

data class ContentItemsItem(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: List<String?>? = null
)

data class DataAward(

	@field:SerializedName("content_items")
	val contentItems: List<ContentItemsItem?>? = null,

	@field:SerializedName("content_page")
	val contentPage: ContentPage? = null
)
