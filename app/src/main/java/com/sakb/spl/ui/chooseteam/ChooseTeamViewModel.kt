package com.sakb.spl.ui.chooseteam

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.data.model.LoginResponse
import com.sakb.spl.data.model.UpdateProfileResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers


/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class ChooseTeamViewModel(
    private val repository: SplRepository/*  private val useCase: getTeamsUseCase,
                                            private val updateProfileUseCase: UpdateProfileUseCase*/)
    : BaseViewModel() {

    var ResultLiveData =  SingleLiveEvent<GetTeamResponse>()
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





    // update profile
    var updateProfileResultLiveData =  SingleLiveEvent<UpdateProfileResponse>()

  /*  fun updateProfile(access_token : String, best_team : String,lang : String)
            =updateProfileUseCase.validateUpdateProfileBestTeam(access_token = access_token,best_team = best_team
    ,lang = lang)*/

    fun updateProfile( best_team : String) {
        repository.UpdateProfileBestTeam( best_team)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    updateProfileResultLiveData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


}