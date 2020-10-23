package com.sakb.spl.ui.home

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetLastFixturesResponse
import com.sakb.spl.data.model.HomePointEldawryResponse
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.data.model.PublicPointEldaweryResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: SplRepository) : BaseViewModel() {

    val homeLiveData = SingleLiveEvent<HomeResponse>()
    val fixturesResponse = SingleLiveEvent<GetLastFixturesResponse>()
    val homePointEldaweryResponse = SingleLiveEvent<HomePointEldawryResponse>()
    val publicPointEldaweryResponse = SingleLiveEvent<PublicPointEldaweryResponse>()

    init {
        //  loadHmeData()
    }

    fun loadHmeData() {
        repository.home()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    homeLiveData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun loadLastFixtureData(){
        repository.getFixtures()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    fixturesResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
    fun loadHomePointEldwry(){
        repository.homePointEldwry()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    homePointEldaweryResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
    fun loadPublicPointEldwry(){
        repository.publicPointEldwry()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    publicPointEldaweryResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}