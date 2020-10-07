package com.sakb.spl.ui.register

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.RegisterResponse
import com.sakb.spl.data.model.SocialResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class RegisterViewModel(private val repository: SplRepository) : BaseViewModel() {

    var provider: String = ""

    var registerResultLiveData = SingleLiveEvent<RegisterResponse>()
    fun register(name: String, email: String, phone: String, pass: String) {
        repository.RegisterUser(name, email, phone, pass)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    registerResultLiveData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    var loginSocialResultLiveData = SingleLiveEvent<SocialResponse>()
    fun loginSocial(provider: String, provider_id: String, name: String) {
        repository.RegisterLoginSocial(provider, provider_id, name)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    loginSocialResultLiveData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


}