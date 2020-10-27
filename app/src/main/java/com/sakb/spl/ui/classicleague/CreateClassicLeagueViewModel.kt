package com.sakb.spl.ui.classicleague

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.CreateLeagueResponse
import com.sakb.spl.data.model.GetAllSubeldawryResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class CreateClassicLeagueViewModel(private val repository: SplRepository) : BaseViewModel() {
    val allSubeldawry = SingleLiveEvent<GetAllSubeldawryResponse>()
    fun loadAllSubeldawry() {
        repository.getAllSubeldawry()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    allSubeldawry.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val createLeagueResponse = SingleLiveEvent<CreateLeagueResponse>()
    fun loadCreateLeague(link_subeldawey: String, name: String) {
        repository.createGroupEldawery(link_subeldawey, name)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    createLeagueResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
