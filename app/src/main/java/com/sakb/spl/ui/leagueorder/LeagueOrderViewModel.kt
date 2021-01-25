package com.sakb.spl.ui.leagueorder

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetAllSubeldawryResponse
import com.sakb.spl.data.model.RankingEldawryResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class LeagueOrderViewModel(private val repository: SplRepository) : BaseViewModel() {

    val rankingTeams = SingleLiveEvent<RankingEldawryResponse>()

    val subEldawry = SingleLiveEvent<GetAllSubeldawryResponse>()

    fun subEldawryLoad() {
        repository.subEldawryRankingTeams()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    subEldawry.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun rankingTeamLoad(link: String) {
        repository.rankingTeams(subeldwryLink = link)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    rankingTeams.value = data
                }, { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()

    }

}