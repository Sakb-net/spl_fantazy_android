package com.sakb.spl.ui.followteams

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.BaseResponse
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class FollowTeamViewModel(private val repository: SplRepository) : BaseViewModel() {

    var ResultLiveData = SingleLiveEvent<GetTeamResponse>()

    //  fun getTeamsxx() = useCase.validateGetTeams()
    fun getTeams() {
        repository.getTeams()
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

    var followTeamResponse = SingleLiveEvent<BaseResponse>()

    fun getFollowTeams(followTeams: String) {
        repository.followTeams(followTeams)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    followTeamResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}