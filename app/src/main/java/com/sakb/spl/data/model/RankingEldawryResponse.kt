package com.sakb.spl.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RankingEldawryResponse(

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DataRanking? = null,

	@field:SerializedName("StatusCode")
	val statusCode: Int? = null,
) : Parcelable

@Parcelize
data class ActiveSubeldwry(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("num_week")
	val numWeek: Int? = null,

	@field:SerializedName("change_point")
	val changePoint: Int? = null,

	@field:SerializedName("is_active")
	val isActive: Int? = null,

	@field:SerializedName("eldwry_id")
	val eldwryId: Int? = null,

	@field:SerializedName("type_id")
	val typeId: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("lang_name")
	val langName: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("update_by")
	val updateBy: String? = null,

	@field:SerializedName("opta_link")
	val optaLink: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null,
) : Parcelable

@Parcelize
data class FormItem(

	@field:SerializedName("second_team_image")
	val secondTeamImage: String? = null,

	@field:SerializedName("state_lang")
	val stateLang: String? = null,

	@field:SerializedName("first_team_goon")
	val firstTeamGoon: String? = null,

	@field:SerializedName("second_team_goon")
	val secondTeamGoon: String? = null,

	@field:SerializedName("first_team_image")
	val firstTeamImage: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("date_day")
	val dateDay: String? = null,

	@field:SerializedName("match_link")
	val matchLink: String? = null,

	@field:SerializedName("location_type")
	val locationType: String? = null,

	@field:SerializedName("first_team_name")
	val firstTeamName: String? = null,

	@field:SerializedName("second_team_name")
	val secondTeamName: String? = null,
) : Parcelable

@Parcelize
data class NextMatch(

	@field:SerializedName("second_team_image")
	val secondTeamImage: String? = null,

	@field:SerializedName("second_team_link")
	val secondTeamLink: String? = null,

	@field:SerializedName("date_day")
	val dateDay: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("match_link")
	val matchLink: String? = null,

	@field:SerializedName("second_team_name")
	val secondTeamName: String? = null,
) : Parcelable

@Parcelize
data class CurrentMatch(

	@field:SerializedName("second_team_image")
	val secondTeamImage: String? = null,

	@field:SerializedName("state_lang")
	val stateLang: String? = null,

	@field:SerializedName("first_team_goon")
	val firstTeamGoon: String? = null,

	@field:SerializedName("second_team_goon")
	val secondTeamGoon: String? = null,

	@field:SerializedName("first_team_image")
	val firstTeamImage: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("date_day")
	val dateDay: String? = null,

	@field:SerializedName("match_link")
	val matchLink: String? = null,

	@field:SerializedName("location_type")
	val locationType: String? = null,

	@field:SerializedName("first_team_name")
	val firstTeamName: String? = null,

	@field:SerializedName("second_team_name")
	val secondTeamName: String? = null,
) : Parcelable

@Parcelize
data class DataRanking(

	@field:SerializedName("ranking_eldwry")
	val rankingEldwry: List<RankingEldwryItem?>? = null,

	@field:SerializedName("active_subeldwry")
	val activeSubeldwry: ActiveSubeldwry? = null,
) : Parcelable

@Parcelize
data class RankingEldwryItem(

	@field:SerializedName("goals_aganist")
	val goalsAganist: String? = null,

	@field:SerializedName("next_match")
	val nextMatch: NextMatch? = null,

	@field:SerializedName("site_team")
	val siteTeam: String? = null,

	@field:SerializedName("count_played")
	val countPlayed: String? = null,

	@field:SerializedName("draw")
	val draw: String? = null,

	@field:SerializedName("team_name")
	val teamName: String? = null,

	@field:SerializedName("team_image")
	val teamImage: String? = null,

	@field:SerializedName("points")
	val points: String? = null,

	@field:SerializedName("team_code")
	val teamCode: String? = null,

	@field:SerializedName("loss")
	val loss: String? = null,

	@field:SerializedName("goals_own")
	val goalsOwn: String? = null,

	@field:SerializedName("form")
	val form: List<FormItem?>? = null,

	@field:SerializedName("goals_diff")
	val goalsDiff: String? = null,

	@field:SerializedName("won")
	val won: String? = null,

	@field:SerializedName("current_match")
	val currentMatch: CurrentMatch? = null,

	@field:SerializedName("team_link")
	val teamLink: String? = null,

	@field:SerializedName("class_state")
	val classState: String? = null,

	var expandList :Boolean = false
) : Parcelable
