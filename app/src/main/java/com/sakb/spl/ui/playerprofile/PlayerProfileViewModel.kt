package com.sakb.spl.ui.playerprofile

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.PlayerResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class PlayerProfileViewModel(
    private val repository: SplRepository
) : BaseViewModel() {


    var playerInfoResultLiveData = SingleLiveEvent<PlayerResponse>()

    /*  fun playerInfoz(
          access_token: String,
          player_link: String,
          lang: String
      ) = playerInfoUseCase.playerInfo(access_token, player_link, lang)*/

    fun playerInfo(
        player_link: String
    ) {
        repository.playerInfo(player_link)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->

                    playerInfoResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


}