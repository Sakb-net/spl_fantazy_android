package  com.sakb.spl.ui.chooseteamplayers

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.AddPlayerResponse
import com.sakb.spl.data.model.AddTeamResponse
import com.sakb.spl.data.model.ChangePlayerResponse
import com.sakb.spl.data.model.PlayerMasterResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class ChooseTeamPlayersViewModel(
    private val repository: SplRepository
/*private val myTeamPlayerUseCase: MyTeamPlayerMasterUseCase*/
) : BaseViewModel() {


    var isMenuPreviewEnabled: Boolean = false


    // handle load team players
    // var MyTeamPlayerResultLiveData = myTeamPlayerUseCase.validateMyTeamPlayersLiveData()
    // var MyTeamPlayersListLiveData = myTeamPlayerUseCase.validateMyTeamPlayersList()

    /**  fun loadMyTeamPlayersx(
    access_token: String,
    lang: String
    ) = myTeamPlayerUseCase.myTeamPlayer(access_token, lang)*/


    var MyTeamPlayersListResultLiveData = SingleLiveEvent<PlayerMasterResponse>()

    fun loadMyTeamPlayers() {

        repository.myTeamPlayerMaster()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    MyTeamPlayersListResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun autoSelectionTeamPlayers() {

        repository.autoSelectionPlayers()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    data?.Message?.let { showSuccessMessage(it) }
                    MyTeamPlayersListResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    fun deleteAllTeamPlayers() {

        repository.deleteAllTeamPlayers()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    data?.Message?.let { showSuccessMessage(it) }
                    MyTeamPlayersListResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    // handle save team
    //var SaveTeamStateLiveData = myTeamPlayerUseCase.validateSaveTeamStateLiveData()
    var SaveTeamResponseLiveData = SingleLiveEvent<AddTeamResponse>()
    /*  fun saveTeamzzz(
          access_token: String,
          name_team: String,
          lang: String
      ) = myTeamPlayerUseCase.saveTeam(access_token, name_team, lang)*/

    fun saveTeam(
        name_team: String
    ) {

        repository.saveTeam(
            name_team
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    SaveTeamResponseLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    /*
    operations on data source
     */

    fun updateData(it: AddPlayerResponse) {
        updatePlayersList(it)
    }

    fun updateData(it: ChangePlayerResponse) {
        updatePlayersList(it)
    }

    private fun updatePlayersList(it: AddPlayerResponse) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data = it.players
            this?.total_team_play = it.data?.total_team_play
            this?.pay_total_cost = it.data?.pay_total_cost
        })

    }

    private fun updatePlayersList(it: ChangePlayerResponse) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data = it.data
            this?.total_team_play = it.total_team_play
            this?.pay_total_cost = it.pay_total_cost
        })

    }


    fun updateAphaData(
        alpha: Float,
        pos: Int,
        parentPos: Int,
        data: PlayerMasterResponse.Data
    ) {
        updateAlphaData(alpha, pos, parentPos, data)
    }

    private fun updateAlphaData(
        alphaValue: Float,
        pos: Int,
        parentPos: Int,
        data: PlayerMasterResponse.Data
    ) {
        MyTeamPlayersListResultLiveData.postValue(MyTeamPlayersListResultLiveData.value.apply {
            this?.data?.get(parentPos)?.get(pos)?.apply {
                alPha = alphaValue
            }

        })
    }


}