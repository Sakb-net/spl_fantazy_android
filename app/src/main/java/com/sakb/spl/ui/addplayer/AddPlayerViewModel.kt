package com.sakb.spl.ui.addplayer

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.AddPlayerResponse
import com.sakb.spl.data.model.ChangePlayerResponse
import com.sakb.spl.data.model.PlayerByTypeResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class AddPlayerViewModel(
    private val repository: SplRepository
) : BaseViewModel() {

    var initSpinnerBefore: Boolean = false
    var recreatedTeam: Boolean = false
    var recreated: Boolean = false
    var orderPlay: String = ""
    var selectedTeamLink: String? = ""

    val ResultLiveData = SingleLiveEvent<PlayerByTypeResponse?>()

    fun getPlayers(
        type_key: String,
        order_play: String,
        link_team: String,
        from_price: String,
        to_price: String
    ) {

        repository.getPlayerByType(
            type_key, order_play, link_team,
            from_price,
            to_price
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    ResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    // Add Player
    var AddPlayerResultLiveData = SingleLiveEvent<AddPlayerResponse?>()

    fun addPlayer(player_link: String) {

        repository.addPlayer(
            player_link
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    AddPlayerResultLiveData.value = data

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