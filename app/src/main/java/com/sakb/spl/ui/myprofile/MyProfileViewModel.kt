package com.sakb.spl.ui.myprofile

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.ProfileResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers


/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class MyProfileViewModel(private val repository: SplRepository) : BaseViewModel() {

    var profileResultLiveData = SingleLiveEvent<ProfileResponse>()

    fun loadMyProfile() {

        repository.myProfile()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    profileResultLiveData.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


}