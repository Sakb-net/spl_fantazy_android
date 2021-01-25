package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForgetPasswordResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataFogetPassword? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
) : Parcelable

@Parcelize
data class DataFogetPassword(

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) : Parcelable
