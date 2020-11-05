package com.sakb.spl.ui.main

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.CardGoldResultResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repository: SplRepository) : BaseViewModel() {
    var getCardGoldResultResponse = SingleLiveEvent<CardGoldResultResponse>()

    fun confirmGoldInfo(
        resourcePath: String,
        checkout_id: String,
    ) {
        repository.confirmGoldInfo(resourcePath, checkout_id)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    getCardGoldResultResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}