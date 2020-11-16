package com.sakb.spl.ui.myleague

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.BaseResponse
import com.sakb.spl.data.model.GetAllLeaguesResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class MyLeagueViewModel(private val repository: SplRepository) : BaseViewModel() {
    val leaguesResponseClassic = SingleLiveEvent<GetAllLeaguesResponse>()

    fun loadAllLeaguesClassic(type_league: String) {
        repository.getAllDawery(type_league)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    leaguesResponseClassic.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val leaguesResponseHead = SingleLiveEvent<GetAllLeaguesResponse>()

    fun loadAllLeaguesHead(type_league: String) {
        repository.getAllDawery(type_league)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    leaguesResponseHead.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val leaveLeague = SingleLiveEvent<BaseResponse>()

    fun loadLeaveLeague(type_league: String, link_league: String) {
        repository.leaveGroup(type_league, link_league)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    leaveLeague.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

}