package com.sakb.spl.ui.contactus

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.ContactUsResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers


class ContactUsViewModel(private val repository: SplRepository) : BaseViewModel() {

    val addState = SingleLiveEvent<Int>()
    val contactUsData = SingleLiveEvent<ContactUsResponse>()

    fun loadContactUs() {
        repository.contactUs()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    contactUsData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun addContactUsMessage(
        content: String
    ) {
        repository.addContactUsMessage(content)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    showSuccessMessage(data?.Message ?: "")
                    addState.value = data?.state_add
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

}
