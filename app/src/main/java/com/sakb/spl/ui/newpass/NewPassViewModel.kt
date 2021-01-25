package com.sakb.spl.ui.newpass

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.CreateNewPasswordResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class NewPassViewModel(private val repository: SplRepository) : BaseViewModel() {
    var newPasswordEvent = SingleLiveEvent<CreateNewPasswordResponse>()

    fun loadCreateNewPassword(email: String, newPassword: String) {
        repository.createNewPassword(email, newPassword)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    newPasswordEvent.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}