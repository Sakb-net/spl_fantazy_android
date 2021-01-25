package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateNewPasswordResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataNewPassword? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null,
) : Parcelable

@Parcelize
data class DataNewPassword(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("team_name")
	val teamName: String? = null,

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("image_best_team")
	val imageBestTeam: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("team_link")
	val teamLink: String? = null,
) : Parcelable
