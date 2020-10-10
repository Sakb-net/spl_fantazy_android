package com.sakb.spl.ui.award

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.AwardResponse
import com.sakb.spl.data.model.StatisticsPlayerResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class AwardViewModel(private val repository: SplRepository):BaseViewModel() {
    val award = SingleLiveEvent<AwardResponse>()

    fun loadAward() {
        repository.getAward()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    award.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

}