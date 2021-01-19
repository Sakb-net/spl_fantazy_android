package com.sakb.spl.data.repository

import com.sakb.spl.data.model.*
import com.sakb.spl.data.remote.SplApiEndpoints
import io.reactivex.Single

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class SplRepository constructor(
    private val remote: SplApiEndpoints,
) {

    fun uploadImage(
        image: String,
    ): Single<UploadImgResponse> {

        return remote.uploadImage(
            image
        )
    }

    fun LoginUser(email: String, pass: String): Single<LoginResponse> {
        return remote.login(
            email_user_name = email,
            password = pass,
            fcm_token = ""
        )
    }

    fun getPlayerByType(
        type_key: String,
        order_play: String,
        link_team: String,
        from_price: String,
        to_price: String,
    ): Single<PlayerByTypeResponse> {
        return remote.getPlayerByType(
            type_key,
            order_play,
            link_team,
            from_price,
            to_price
        )
    }

    fun myProfile(
    ): Single<ProfileResponse> {
        return remote.myProfile(
        )
    }

    fun saveTeam(
        name_team: String,
    ): Single<AddTeamResponse> {
        return remote.saveTeam(name_team)
    }

    fun playerInfo(
        player_link: String,
    ): Single<PlayerResponse> {
        return remote.playerInfo(
            player_link
        )
    }

    fun addCaptainOrViceCaptain(
        player_link: String,
        type: String,
    ): Single<AddCaptainOrVise> {
        return remote.addCaptainOrViseCaptain(
            player_link, type
        )
    }

    fun instruction(): Single<HowToPlayResponse> {
        return remote.instruction()
    }

    fun addPlayer(
        player_link: String,
    ): Single<AddPlayerResponse> {
        return remote.addPlayer(player_link)
    }

    fun changePlayer(
        eldwry_link: String,
        delet_player_link: String,
        add_player_link: String,
    ): Single<ChangePlayerResponse> {
        return remote.changePlayer(
            eldwry_link,
            delet_player_link,
            add_player_link
        )
    }

    fun myTeamPlayer(
    ): Single<MyteamPlayersResponse> {
        return remote.myTeamPlayer()
    }

    fun addDirectInsideChange(
        player_link_one: String,
        player_link_two: String,
    ): Single<AddDirectInsideChange> {
        return remote.addDirectInsideChange(
            player_link_one,
            player_link_two
        )
    }

    fun checkInsideChange(
        player_link: String,
    ): Single<CheckInsideChangeResponse> {
        return remote.checkInsideChange(
            player_link = player_link,
            ch_game_player_id_one = "0",
            ch_player_id_one = "0",
            ch_game_player_id_two = "0",
            ch_player_id_two = "0"
        )
    }

    fun myTeamPlayerMaster(): Single<PlayerMasterResponse> {
        return remote.chooseMyTeamPlayer()
    }

    fun autoSelectionPlayers(): Single<PlayerMasterResponse> {
        return remote.autoSelectionPlayer()
    }

    fun deleteAllTeamPlayers(

    ): Single<PlayerMasterResponse> {
        return remote.deleteAllTeamPlayers()
    }

    fun RegisterUser(
        name: String,
        email: String,
        phone: String,
        pass: String,
    ): Single<RegisterResponse> {
        return remote.register(

            email = email,
            phone = phone,
            password = pass,
            display_name = name,
            reg_site = "android",
            fcm_token = ""
        )
    }

    fun RegisterLoginSocial(
        provider: String,
        provider_id: String,
        name: String,
    ): Single<SocialResponse> {
        return remote.registerLoginSocial(
            provider = provider,
            provider_id = provider_id,
            display_name = name,
            reg_site = "android",
            fcm_token = ""
        )
    }

    fun UpdateProfile(
        best_team: String,
        display_name: String,
        email: String,
        image: String,
    ): Single<UpdateProfileResponse> {
        return remote.updateProfile(
            best_team = best_team,
            display_name = display_name,
            email = email,
            image = image

        )
    }

    fun UpdateProfileBestTeam(
        best_team: String,

        ): Single<UpdateProfileResponse> {
        return remote.updateProfileBestTeam(

            best_team = best_team

        )
    }

    fun changePassword(
        old_password: String,
        new_password: String,

        ): Single<ChangePasswordResponse> {
        return remote.changePassword(
            old_password, new_password


        )
    }

    fun terms(): Single<TermsResponse> {
        return remote.terms()
    }

    fun getTeams(): Single<GetTeamResponse> {
        return remote.getTeams()
    }

    fun videos(num_page: String, limit: String): Single<VideosResponse> {
        return remote.videos(
            num_page = num_page,
            limit = limit
        )
    }

    fun news(num_page: String, limit: String): Single<NewsResponse> {
        return remote.news(
            num_page = num_page,
            limit = limit
        )
    }

    fun home(): Single<HomeResponse> {
        return remote.home("", "")
    }

    fun homePointEldwry(): Single<HomePointEldawryResponse> {
        return remote.homePointEldwry()
    }

    fun publicPointEldwry(): Single<PublicPointEldaweryResponse> {
        return remote.publicPointEldwry()
    }

    fun contactUs(): Single<ContactUsResponse> {
        return remote.contactUsData()
    }

    fun addContactUsMessage(content: String): Single<AddContactUsMessageResponse> {
        return remote.addContactUsMessage(content)
    }

    fun getFixtures(): Single<GetLastFixturesResponse> {
        return remote.getFixtures()
    }

    fun getAllSubeldawry(): Single<GetAllSubeldawryResponse> {
        return remote.getAllSubeldawry()
    }

    fun getAllFixturesBySubeldawry(link_subeldawey: String): Single<GetFixturesBySubeldawryResponse> {
        return remote.getAllFixturesBySubeldawry(link_subeldawey)
    }

    fun getFixturesBy(link_subeldawey: String): Single<GetFixtuersResponse> {
        return remote.getFixturesBy(link_subeldawey)
    }

    fun getStatistics(
        link_team: String = "",
        order_play: String = "",
        loc_player: String = "",
    ): Single<StatisticsPlayerResponse> {
        return remote.getStatistics(link_team, order_play, loc_player)
    }

    fun getAward(): Single<AwardResponse> {
        return remote.award()
    }

    fun getPointsEldawry(): Single<PointsEldwryResponse> {
        return remote.getPointsEldawry()
    }

    fun getPointSubeldawry(link_team: String): Single<GetPointSubeldawryResponse> {
        return remote.getPointsSubEldawry(link_team)
    }

    fun checkCardStatus(): Single<CardStatusResponse> {
        return remote.checkCardsStatus()
    }

    fun activeTripleCard(): Single<BaseResponse> {
        return remote.activeTripleCardsStatus()
    }

    fun activeBenchCard(): Single<BaseResponse> {
        return remote.activeBenchCardsStatus()
    }

    fun cancelCards(type_key: String): Single<BaseResponse> {
        return remote.cancelCardsStatus(type_key)
    }

    fun cardStatusTransfer(type: String): Single<CardStatusTransferResponse> {
        return remote.getStatusCardInside(type)
    }

    fun createGroupEldawery(
        link_subeldawey: String,
        name: String,
        type_league: String,
    ): Single<CreateLeagueResponse> {
        return remote.createGroupEldawery(link_subeldawey, name, type_league)
    }

    fun joinGroupEldawery(val_code: String, type_league: String): Single<JoinLeagueResponse> {
        return remote.joinGroupEldawery(type_league, val_code)
    }

    fun getAllDawery(type_league: String): Single<GetAllLeaguesResponse> {
        return remote.getAllDawery(type_league)
    }

    fun getStandingDawery(
        type_league: String,
        link_league: String,
        link_subeldawey: String,
    ): Single<StandingResponse> {
        return remote.getStandingList(type_league, link_league, link_subeldawey)
    }

    fun getGroupSubEldawry(
        type_league: String,
        link_league: String,
    ): Single<GroupSubEldawryResponse> {
        return remote.getGroupSubEldawry(type_league, link_league)
    }

    fun getSettingsGroupsEldawry(
        type_league: String,
        link_league: String,
    ): Single<SettingGroupsResponse> {
        return remote.getSettingsGroupsEldawry(type_league, link_league)
    }

    fun putUpdateGroupEldawry(
        type_league: String, link_league: String, link_sub: String, name: String,
    ): Single<UpdateGroupResponse> {
        return remote.updateGroup(type_league, link_league, link_sub, name)
    }

    fun deleteGroupEldawry(
        type_league: String,
        link_league: String,
    ): Single<BaseResponse> {
        return remote.deleteGroup(type_league, link_league)
    }

    fun leaveGroup(
        type_league: String,
        link_league: String,
    ): Single<BaseResponse> {
        return remote.leaveGroup(type_league, link_league)
    }

    fun switchAdminGroup(
        type_league: String,
        link_league: String,
        user_name: String,
    ): Single<BaseResponse> {
        return remote.switchAdmin(type_league, link_league, user_name)
    }

    fun deletePlayer(
        type_league: String,
        link_league: String,
        user_name: String,
    ): Single<DeletePlayerResponse> {
        return remote.deletePlayer(type_league, link_league, user_name)
    }

    fun getSubDefault(arrayPlayer: String, activeCardGray: String): Single<GetSubDefaultResponse> {
        return remote.getSubDefault(arrayPlayer, activeCardGray)
    }

    fun getGoldInfo(arrayPlayer: String): Single<CardGoldInfoResponse> {
        return remote.getGoldInfo(arrayPlayer)
    }

    fun confirmGoldInfo(resourcePath: String, checkout_id: String): Single<CardGoldResultResponse> {
        return remote.confirmGoldInfo(resourcePath, checkout_id)
    }

    fun followTeams(followTeams: String): Single<BaseResponse> {
        return remote.followTeams(followTeams)
    }
}