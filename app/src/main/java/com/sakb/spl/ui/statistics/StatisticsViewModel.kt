package com.sakb.spl.ui.statistics

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.data.model.StatisticsPlayerResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class StatisticsViewModel(private val repository: SplRepository) : BaseViewModel() {
    val statisticsPlayer = SingleLiveEvent<StatisticsPlayerResponse>()
    var initSpinnerBefore: Boolean = false
    var recreatedTeam: Boolean = false
    var recreatedOrder: Boolean = false
    var recreatedLocation: Boolean = false
    var orderPlay: String = ""
    var locPlayer: String = ""
    var selectedTeamLink: String? = ""

    val team = SingleLiveEvent<GetTeamResponse>()

    fun loadStatistics(link_team: String = "", order_play: String = "", loc_player: String = "") {
        repository.getStatistics(link_team, order_play, loc_player)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    statisticsPlayer.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun loadTeams() {
        repository.getTeams()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    team.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
