package com.sakb.spl.ui.mypoints

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetPointSubeldawryResponse
import com.sakb.spl.data.model.PointsEldwryResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class MyPointsViewModel(private val repository: SplRepository) : BaseViewModel(){
    val pointsEldwry = SingleLiveEvent<PointsEldwryResponse>()

    val pointsSubeldwry = SingleLiveEvent<GetPointSubeldawryResponse>()

    var isMenuPreviewEnabled: Boolean = false

    fun loadPointsEldwry() {
        repository.getPointsEldawry()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    pointsEldwry.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun loadPointsSubeldwry(linkTeam:String) {
        repository.getPointSubeldawry(linkTeam)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    pointsSubeldwry.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
