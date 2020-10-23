package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomePointEldawryResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataHome? = null,

	@field:SerializedName("home_points")
	val homePoints: HomePoints? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null
) : Parcelable

@Parcelize
data class HomePoints(

	@field:SerializedName("heigh_point")
	val heighPoint: Int? = null,

	@field:SerializedName("total_user")
	val totalUser: Int? = null,

	@field:SerializedName("user_total_mypoint")
	val userTotalSum: Int? = null,

	@field:SerializedName("total_avg")
	val totalAvg: Int? = null,

	@field:SerializedName("total_sum")
	val totalSum: Int? = null
) : Parcelable

@Parcelize
data class DataHome(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("num_week")
	val numWeek: Int? = null,

	@field:SerializedName("cost")
	val cost: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("name_num_week")
	val nameNumWeek: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
) : Parcelable
