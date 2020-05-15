package com.sakb.spl.ui.changepassword

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.repository.SplRepository
import io.reactivex.schedulers.Schedulers

class ChangePasswordViewModel(private val repository: SplRepository) : BaseViewModel() {

    fun changePassword(
        old_password: String,
        new_password: String
    ) {
        repository.changePassword(
          old_password, new_password
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    showSuccessMessage(data?.Message?:"")
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


}