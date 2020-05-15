package com.sakb.spl.ui.terms


import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.TermsResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers


class TermsViewModel (private val repository: SplRepository)
    : BaseViewModel() {

    var termsResultLiveData =  SingleLiveEvent<TermsResponse>()
    fun terms() {
        repository.terms()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    termsResultLiveData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


}
