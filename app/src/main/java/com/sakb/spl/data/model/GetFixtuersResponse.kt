package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetFixtuersResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataItemSubFix? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
) : Parcelable
