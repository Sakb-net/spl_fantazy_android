package com.sakb.spl.ui.statistics

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.StatisticsPlayerResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class StatisticsViewModel(private val repository: SplRepository) : BaseViewModel(){
    val statisticsPlayer = SingleLiveEvent<StatisticsPlayerResponse>()

    fun loadStatistics(){
        repository.getStatistics()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    statisticsPlayer.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
