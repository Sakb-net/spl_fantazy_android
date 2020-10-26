package com.sakb.spl.ui.transfers

import androidx.lifecycle.MutableLiveData
import com.google.android.youtube.player.internal.t
import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.*
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers


class TransfersViewModel(private val repository: SplRepository) : BaseViewModel() {


    var isMenuPreviewEnabled: Boolean = false

    var playersSubtitleList :MutableLiveData<MutableList<PlayersSubtitle>> = MutableLiveData()

    // handle load team players
    // var MyTeamPlayerResultLiveData = myTeamPlayerUseCase.validateMyTeamPlayersLiveData()
    // var MyTeamPlayersListLiveData = myTeamPlayerUseCase.validateMyTeamPlayersList()

    /**  fun loadMyTeamPlayersx(
    access_token: String,
    lang: String
    ) = myTeamPlayerUseCase.myTeamPlayer(access_token, lang)*/


    var MyTeamPlayersListResultLiveData = SingleLiveEvent<PlayerMasterResponse>()
    fun loadMyTeamPlayers() {
        repository.myTeamPlayerMaster()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    MyTeamPlayersListResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    var SaveTeamResponseLiveData = SingleLiveEvent<AddTeamResponse>()
    fun saveTeam(name_team: String) {
        repository.saveTeam(name_team)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    SaveTeamResponseLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun updateData(it: AddPlayerResponse) {
        updatePlayersList(it)
    }

    fun updateData(it: ChangePlayerResponse) {
        updatePlayersList(it)
    }
    fun updateData(it: PlayerMasterResponse) {
        updatePlayersList(it)
    }

    private fun updatePlayersList(it: AddPlayerResponse) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data = it.players
            this?.total_team_play = it.data?.total_team_play
            this?.pay_total_cost = it.data?.pay_total_cost
        })

    }

    private fun updatePlayersList(it: ChangePlayerResponse) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data = it.data
            this?.total_team_play = it.total_team_play
            this?.pay_total_cost = it.pay_total_cost
        })

    }

    private fun updatePlayersList(it: PlayerMasterResponse) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data = it.data
            this?.total_team_play = it.total_team_play
            this?.pay_total_cost = it.pay_total_cost
        })
    }


    fun updateAphaData(
        alpha: Float,
        pos: Int,
        parentPos: Int
    ) {
        updateAlphaData(alpha, pos, parentPos/*, data*/)
    }

    private fun updateAlphaData(
        alphaValue: Float,
        pos: Int,
        parentPos: Int
        //,
        //data: PlayerMasterResponse.Data
    ) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data?.get(parentPos)?.get(pos)?.apply {
                alPha = alphaValue
            }

        })
    }


    val playerResponse = SingleLiveEvent<PlayerResponse>()

    fun loadPlayerInfo(link_player:String) {
        repository.playerInfo(link_player)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    playerResponse.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val playerInResponse = SingleLiveEvent<PlayerResponse>()

    fun loadInPlayerInfo(link_player:String) {
        repository.playerInfo(link_player)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    playerInResponse.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
    val playerOutResponse = SingleLiveEvent<PlayerResponse>()

    fun loadOutPlayerInfo(link_player:String) {
        repository.playerInfo(link_player)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    playerOutResponse.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    // change player
    var changePlayerResultLiveData = SingleLiveEvent<ChangePlayerResponse?>()

    fun changePlayer(
        eldwry_link: String,
        delet_player_link: String,
        add_player_link: String
    ) {
        repository.changePlayer(
            eldwry_link,
            delet_player_link,
            add_player_link
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    changePlayerResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}