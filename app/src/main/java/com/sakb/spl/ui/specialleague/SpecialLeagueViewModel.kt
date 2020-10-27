package com.sakb.spl.ui.specialleague

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.JoinLeagueResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers


class SpecialLeagueViewModel(private val repository: SplRepository) : BaseViewModel() {
    val joinLeagueResponse = SingleLiveEvent<JoinLeagueResponse>()
    fun loadJoinLeague(valCode: String = "") {
        repository.joinGroupEldawery(valCode)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    joinLeagueResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
