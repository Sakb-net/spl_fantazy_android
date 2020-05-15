package com.sakb.spl.data.model
import com.google.gson.annotations.SerializedName


data class CheckInsideChangeResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("StatusCode")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("all_hide")
        var allHide: Int?,
        @SerializedName("ch_game_player_id_one")
        var chGamePlayerIdOne: Int?,
        @SerializedName("ch_game_player_id_two")
        var chGamePlayerIdTwo: String?,
        @SerializedName("ch_player_id_one")
        var chPlayerIdOne: Int?,
        @SerializedName("ch_player_id_two")
        var chPlayerIdTwo: String?,
        @SerializedName("change")
        var change: Int?,
        @SerializedName("first_choose")
        var firstChoose: Int?,
        @SerializedName("msg_add")
        var msgAdd: String?,
        @SerializedName("ok_add")
        var okAdd: Int?,
        @SerializedName("type_loc_player")
        var typeLocPlayer: String?,
        @SerializedName("type_loc_player_one")
        var typeLocPlayerOne: String?,
        @SerializedName("type_loc_player_two")
        var typeLocPlayerTwo: Any?
    )
}