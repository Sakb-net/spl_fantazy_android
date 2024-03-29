package com.sakb.spl.data.remote

import com.sakb.spl.data.model.*
import io.reactivex.Single
import retrofit2.http.*
/**
 * Created by dev.mahmoud_ashraf on 10/3/2019.
 */
interface SplApiEndpoints {

    @FormUrlEncoded
    @POST("/api/v1/login/email")
    fun login(
        @Field("email_user_name") email_user_name: String,
        @Field("password") password: String,
        @Field("fcm_token") fcm_token: String
    ): Single<LoginResponse>

    @FormUrlEncoded
    @POST("/api/v1/filterPlayer")
    fun getPlayerByType(
        @Field("type_key") type_key: String,
        @Field("order_play") order_play: String,
        @Field("link_team") link_team: String,
        @Field("from_price") from_price: String,
        @Field("to_price") to_price: String
    ): Single<PlayerByTypeResponse>

    @FormUrlEncoded
    @POST("/api/v1/register")
    fun register(
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("display_name") display_name: String,
        @Field("reg_site") reg_site: String,
        @Field("fcm_token") fcm_token: String
    ): Single<RegisterResponse>

    @FormUrlEncoded
    @POST("/api/v1/change_password")
    fun changePassword(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String
    ): Single<ChangePasswordResponse>

    @FormUrlEncoded
    @POST("/api/v1/login/social")
    fun registerLoginSocial(
        @Field("provider") provider: String,
        @Field("provider_id") provider_id: String,
        @Field("display_name") display_name: String,
        @Field("reg_site") reg_site: String,
        @Field("fcm_token") fcm_token: String
    ): Single<SocialResponse>

    @FormUrlEncoded
    @POST("/api/v1/add_player")
    fun addPlayer(
        @Field("player_link") player_link: String
    ): Single<AddPlayerResponse>

    @FormUrlEncoded
    @POST("/api/v1/change_player")
    fun changePlayer(
        @Field("eldwry_link") eldwry_link: String,
        @Field("delet_player_link") delet_player_link: String,
        @Field("add_player_link") add_player_link: String
    ): Single<ChangePlayerResponse>

    @FormUrlEncoded
    @POST("/api/v1/check_insideChange")
    fun checkInsideChange(
        @Field("player_link") player_link: String,
        @Field("ch_game_player_id_one") ch_game_player_id_one: String,
        @Field("ch_player_id_one") ch_player_id_one: String,
        @Field("ch_game_player_id_two") ch_game_player_id_two: String,
        @Field("ch_player_id_two") ch_player_id_two: String
    ): Single<CheckInsideChangeResponse>

    @FormUrlEncoded
    @POST("/api/v1/player")
    fun playerInfo(
        @Field("player_link") player_link: String
    ): Single<PlayerResponse>

    @FormUrlEncoded
    @POST("/api/v1/add_captain_assist")
    fun addCaptainOrViseCaptain(
        @Field("player_link") player_link: String,
        @Field("type") type: String
    ): Single<AddCaptainOrVise>

    @POST("/api/v1/instraction")
    fun instruction(): Single<HowToPlayResponse>

    @POST("/api/v1/auto_selection_player")
    fun autoSelectionPlayer(): Single<PlayerMasterResponse>

    @POST("/api/v1/reset_all_player")
    fun deleteAllTeamPlayers(): Single<PlayerMasterResponse>


    @FormUrlEncoded
    @POST("/api/v1/add_myteam")
    fun saveTeam(
        @Field("name_team") name_team: String
    ): Single<AddTeamResponse>

    // get all players in my team
    @POST("/api/v1/player_myteam")
    fun myTeamPlayer(): Single<MyteamPlayersResponse>

    // get all players in my team
    @GET("/api/v1/profile")
    fun myProfile(): Single<ProfileResponse>

    @FormUrlEncoded
    @POST("/api/v1/add_direct_insideChange")
    fun addDirectInsideChange(
        @Field("player_link_one") player_link_one: String,
        @Field("player_link_two") player_link_two: String
    ): Single<AddDirectInsideChange>


    // get all players in my team
    @POST("/api/v1/player_master")
    fun chooseMyTeamPlayer(): Single<PlayerMasterResponse>


    @POST("/api/v1/terms")
    fun terms(): Single<TermsResponse>


    @GET("/api/v1/get_teams")
    fun getTeams(): Single<GetTeamResponse>

    @FormUrlEncoded
    @POST("/api/v1/update_profile")
    fun updateProfile(
        @Field("best_team") best_team: String,
        @Field("display_name") display_name: String,
        @Field("email") email: String,
        @Field("image") image: String
    ): Single<UpdateProfileResponse>

    @FormUrlEncoded
    @POST("/api/v1/update_profile")
    fun updateProfileBestTeam(
        @Field("best_team") best_team: String
    ): Single<UpdateProfileResponse>


    @FormUrlEncoded
    @POST("/api/v1/videos")
    fun videos(
        @Field("num_page") num_page: String,
        @Field("limit") limit: String
    ): Single<VideosResponse>


    @FormUrlEncoded
    @POST("/api/v1/news")
    fun news(
        @Field("num_page") num_page: String,
        @Field("limit") limit: String
    ): Single<NewsResponse>


    @FormUrlEncoded
    @POST("/api/v1/home")
    fun home(
        @Field("limit") limit: String,
        @Field("limit_fix") limit_fix: String
    ): Single<HomeResponse>


    @FormUrlEncoded
    @POST("/api/v1/comments")
    fun comments(
        @Field("num_page") num_page: String,
        @Field("limit") limit: String,
        @Field("lang") lang: String,
        @Field("link") link: String
    ): Single<CommentsResponse>


    @FormUrlEncoded
    @POST("api/v1/uploadImage")
    fun uploadImage(
        @Field("image") image  : String
    ): Single<UploadImgResponse>


    @POST("api/v1/contact_us")
    fun contactUsData(): Single<ContactUsResponse>

    @FormUrlEncoded
    @POST("api/v1/add_contact_us")
    fun addContactUsMessage(
        @Field("content") content : String
    ): Single<AddContactUsMessageResponse>
}