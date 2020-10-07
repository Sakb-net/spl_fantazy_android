package com.sakb.spl.ui.howtoplay

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.HowToPlayResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class HowToPlayViewModel(private val repository: SplRepository) : BaseViewModel() {

    var instructionsListLiveData = SingleLiveEvent<List<HowToPlayResponse.ContentRole>>()

    init {
        // howToPlayUseCase.instructions(PrefManager.getLanguage())
    }

    fun instructions() {
        repository.instruction()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    data.data?.contentRole?.let {
                        instructionsListLiveData.value = it
                    }

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    fun updateColapseState(position: Int) {
        updateColapseStatee(position)
    }

    private fun updateColapseStatee(position: Int) {
        val list = instructionsListLiveData.value
        val newList = list?.mapIndexed { index, RoleUiModel ->
            when {
                index == position -> {
                    RoleUiModel.copy(isActivated = RoleUiModel.isActivated?.not())
                }
                else -> {
                    RoleUiModel.copy()
                }
            }
        }

        instructionsListLiveData.postValue(newList)
    }


}
