package com.sakb.spl.ui.help

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.HowToPlayResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class HelpViewModel (
    private  val repository: SplRepository
) : BaseViewModel() {


    init {
       // howToPlayUseCase.instructions(PrefManager.getLanguage())
    }




    ///////////////
    var helpListLiveData = SingleLiveEvent<List<HowToPlayResponse.ContentHelp>>()//howToPlayUseCase.helpListLiveData()

    fun help() {
        repository.instruction()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    data?.data?.contentHelp?.let {

                        helpListLiveData.value = it
                    }

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun updateColapseState(position: Int) {
        updateHelpColapseState(position)
    }

   private fun updateHelpColapseState(position: Int) {
        val list =   helpListLiveData.value
        val newList = list?.mapIndexed { index, RoleUiModel ->
            when{
                index == position-> {
                    RoleUiModel.copy(isActivated = RoleUiModel.isActivated.not())
                }
                else -> {
                    RoleUiModel.copy()
                }
            }}

       helpListLiveData.value = newList
    }


}
