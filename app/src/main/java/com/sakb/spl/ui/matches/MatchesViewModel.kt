package com.sakb.spl.ui.matches

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.*
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class MatchesViewModel(private val repository: SplRepository) : BaseViewModel(){
    val allSubeldawry = SingleLiveEvent<GetAllSubeldawryResponse>()

    val allFixturesBySubeldawry  = SingleLiveEvent<GetFixturesBySubeldawryResponse>()
    val fixturesBy  = SingleLiveEvent<GetFixtuersResponse>()

    fun loadAllSubeldawry(){
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

    fun loadAllFixturesBySubelawry(link_subeldawry:String){
        repository.getAllFixturesBySubeldawry(link_subeldawry)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    allFixturesBySubeldawry.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
    fun loadFixturesBy(link_subeldawry:String){
        repository.getFixturesBy(link_subeldawry)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    fixturesBy.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
