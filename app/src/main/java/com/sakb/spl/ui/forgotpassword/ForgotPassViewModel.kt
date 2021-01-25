package com.sakb.spl.ui.forgotpassword

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.ConfirmResetPasswordResponse
import com.sakb.spl.data.model.ForgetPasswordResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class ForgotPassViewModel(private val repository: SplRepository) : BaseViewModel() {

    var forgetPassword = SingleLiveEvent<ForgetPasswordResponse>()
    var confirmResetPassword = SingleLiveEvent<ConfirmResetPasswordResponse>()

    fun loadForgetPassword(email: String) {
        repository.forgetPassword(email)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    forgetPassword.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun loadConfirmPassword(email: String, token: String) {
        repository.confirmForgetPassword(email,token)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    confirmResetPassword.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

}