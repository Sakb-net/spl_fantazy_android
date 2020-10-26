package com.sakb.spl.ui.login

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.LoginResponse
import com.sakb.spl.data.model.SocialResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class LoginViewModel(private val repository: SplRepository) : BaseViewModel() {

    var provider: String = ""

    var loginResultLiveData = SingleLiveEvent<LoginResponse>()
    //fun login(email: String, pass : String) = useCase.validateLogin(email, pass)

    fun login(email: String, pass: String) {
        repository.LoginUser(email, pass)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    loginResultLiveData.value = data
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