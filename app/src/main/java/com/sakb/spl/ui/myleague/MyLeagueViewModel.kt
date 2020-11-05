package com.sakb.spl.ui.myleague

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetAllLeaguesResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class MyLeagueViewModel(private val repository: SplRepository) : BaseViewModel() {
    val leaguesResponse = SingleLiveEvent<GetAllLeaguesResponse>()

    fun loadAllLeagues() {
        repository.getAllDawery()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    leaguesResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

}