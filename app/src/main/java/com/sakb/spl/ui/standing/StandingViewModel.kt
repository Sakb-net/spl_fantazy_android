package com.sakb.spl.ui.standing

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GroupSubEldawryResponse
import com.sakb.spl.data.model.StandingResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class StandingViewModel(private val repository: SplRepository) : BaseViewModel() {
    val standingResponse = SingleLiveEvent<StandingResponse>()
    var recreatedStanding: Boolean = false
    var selectedLink: String? = ""
    fun loadStanding(type_league: String, link_league: String = "", link_sub: String = "") {
        repository.getStandingDawery(type_league, link_league, link_sub)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    standingResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val groupSubEldawryResponse = SingleLiveEvent<GroupSubEldawryResponse>()
    fun loadSubeldawry(type_league: String, link_league: String = "") {
        repository.getGroupSubEldawry(type_league, link_league)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    groupSubEldawryResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}