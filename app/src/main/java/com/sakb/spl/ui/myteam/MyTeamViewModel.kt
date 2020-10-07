package com.sakb.spl.ui.myteam

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.AddCaptainOrVise
import com.sakb.spl.data.model.AddDirectInsideChange
import com.sakb.spl.data.model.MyteamPlayersResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class MyTeamViewModel(
    private val repository: SplRepository
    /*  private val myTeamPlayerUseCase: MyTeamPlayerUseCase*/
) : BaseViewModel() {


    var playerLinkOne: String = ""
    var selectedPlayer: Int = 0
    var firstPlayerLink: String? = ""
    var isMenuPreviewEnabled: Boolean = false


    var MyTeamPlayersListLiveData = SingleLiveEvent<MyteamPlayersResponse>()
    fun loadMyTeamPlayers(
    ) {
        repository.myTeamPlayer()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->

                    MyTeamPlayersListLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    var substitutesList =
        SingleLiveEvent<List<MyteamPlayersResponse.Player>>() //myTeamPlayerUseCase.substitutesListLiveData


    //  var checkAvailablePlayerToSwapStatusLiveData = myTeamPlayerUseCase.checkInsideChangeResponseResultLiveData()
    //  var checkAvailablePlayerToSwapData = myTeamPlayerUseCase.checkInsideChangeResponseData()

    fun checkAvailablePlayerToSwap(
        //player_link: String,
        type_loc_player: String,
        childPos: Int,
        parentPos: Int,
        isMainPlayer: Boolean
    ) = checkInsideChange(/* player_link,*/   childPos,
        parentPos, isMainPlayer, type_loc_player
    )


    private fun checkInsideChange(
        //player_link: String,
        childPos: Int,
        parentPos: Int,
        isMainPlayer: Boolean,
        type_loc_player: String
    ) {
        //  if (validateResponseResult.value != null) return

        /**  if (compositeDisposable.isDisposed)
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(splRepo.checkInsideChange(access_token, player_link, lang)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe
        {
        checkInsideChangeResponseResult.postValue(Event(NetworkState.LOADING))
        }
        .subscribe(
        { data ->
        data?.let {
        it.data?.allHide?.let { allHide ->
        Timber.e("all hidden is $allHide")
        updateActivePlayerForChange(
        ""+ it.data?.typeLocPlayerOne,
        childPos, parentPos,
        allHide,
        isMainPayerClicked = isMainPlayer
        )
        }
        checkInsideChangeResponseData.postValue(it)
        checkInsideChangeResponseResult.postValue(Event(NetworkState.LOADED(it)))
        } ?: run {
        checkInsideChangeResponseResult.postValue(Event(NetworkState.error("data is finished!")))
        }
        },
        { throwable ->
        if (throwable is IOException) {
        checkInsideChangeResponseResult.postValue(
        Event(
        NetworkState.error(
        application.getString(R.string.need_internet)
        )
        )
        )
        } else
        checkInsideChangeResponseResult.postValue(
        Event(
        NetworkState.error(
        throwable.message
        ?: application.getString(R.string.something_wrong)
        )
        )
        )
        }
        )
        )*/

        //  val myteamPlayers = validateResponseData.value?.data
        MyTeamPlayersListLiveData.postValue(MyTeamPlayersListLiveData.value.apply {

            if (isMainPlayer)
                this?.data?.mapIndexed { index, list ->
                    when {
                        index == parentPos -> {
                            list.mapIndexed { playerIndex, player ->
                                if (playerIndex == childPos) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = true
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }

                            }
                        }
                        index == 4 -> {
                            if (type_loc_player == "goalkeeper") {
                                list.map {
                                    if (it.type_loc_player == "goalkeeper") {
                                        it.alPha = 1.0f
                                        it.isActiveToSwap = true
                                        it.isSelected = false
                                    } else {
                                        it.alPha = 0.5f
                                        it.isActiveToSwap = false
                                        it.isSelected = false
                                    }
                                }


                            } else if (type_loc_player == "defender") {

                                if (this.data?.get(1)?.size == 3) {
                                    list.map {
                                        if (it.type_loc_player == "defender") {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        }
                                    }
                                } else {

                                    list.map {
                                        if (it.type_loc_player == "goalkeeper") {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        }
                                    }
                                }


                            } else if (type_loc_player == "attacker") {

                                if (this.data?.get(3)?.size == 1) {
                                    list.map {
                                        if (it.type_loc_player == "attacker") {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        }
                                    }
                                } else {

                                    list.map {
                                        if (it.type_loc_player == "goalkeeper") {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        }
                                    }
                                }


                            } else if (type_loc_player == "line") {

                                if (this.data?.get(2)?.size == 2) {
                                    list.map {
                                        if (it.type_loc_player == "line") {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        }
                                    }
                                } else {

                                    list.map {
                                        if (it.type_loc_player == "goalkeeper") {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        }
                                    }
                                }


                            } else {

                            }
                        }
                        else -> {
                            list.mapIndexed { _, player ->
                                player.alPha = 0.5f
                                player.isActiveToSwap = false
                                player.isSelected = false
                            }


                        }

                    }
                }
            else {
                this?.data?.mapIndexed { index, list ->
                    when (index) {

                        0 -> {
                            list.map { player ->
                                if (type_loc_player == "goalkeeper") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        1 -> {

                            list.mapIndexed { _, player ->
                                if (type_loc_player != "defender" && type_loc_player != "goalkeeper" && list.size > 3) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else if (type_loc_player == "defender") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        2 -> {
                            list.mapIndexed { _, player ->
                                if (type_loc_player != "line" && type_loc_player != "goalkeeper" && list.size > 2) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else if (type_loc_player == "line") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        3 -> {
                            list.mapIndexed { _, player ->
                                if (type_loc_player != "attacker" && type_loc_player != "goalkeeper" && list.size > 1) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else if (type_loc_player == "attacker") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        4 -> {
                            list.mapIndexed { playerIndex, player ->
                                if (parentPos == index && playerIndex == childPos) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = true
                                } else if (type_loc_player != "goalkeeper") {
                                    if (player.type_loc_player == "goalkeeper") {
                                        player.alPha = 0.5f
                                        player.isActiveToSwap = false
                                        player.isSelected = false
                                    } else {
                                        player.alPha = 1.0f
                                        player.isActiveToSwap = true
                                        player.isSelected = false
                                    }

                                } else if (type_loc_player == "goalkeeper") {
                                    if (player.type_loc_player == "goalkeeper") {
                                        player.alPha = 1.0f
                                        player.isActiveToSwap = true
                                        player.isSelected = false
                                    } else {
                                        player.alPha = 0.5f
                                        player.isActiveToSwap = false
                                        player.isSelected = false
                                    }

                                }

                            }
                        }
                        else -> Unit
                    }
                }

            }


        })

    }


    fun loadAvailablePlayerToSwap(
        // player_link: String,
        type_loc_player: String,
        childPos: Int,
        parentPos: Int,
        isMainPlayer: Boolean
    ) = checkAndLoadInsideChange( /*player_link, */  childPos,
        parentPos, isMainPlayer, type_loc_player
    )

    private fun checkAndLoadInsideChange(
        //player_link: String,
        childPos: Int,
        parentPos: Int,
        isMainPlayer: Boolean,
        type_loc_player: String
    ) {

        MyTeamPlayersListLiveData.postValue(MyTeamPlayersListLiveData.value.apply {

            if (isMainPlayer)
                this?.data?.mapIndexed { index, list ->
                    when {
                        index == parentPos -> {
                            list.mapIndexed { playerIndex, player ->
                                if (playerIndex == childPos) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = true
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }

                            }
                        }
                        index == 4 -> {
                            if (type_loc_player == "goalkeeper") {
                                list.map {
                                    if (it.type_loc_player == "goalkeeper") {
                                        it.alPha = 1.0f
                                        it.isActiveToSwap = true
                                        it.isSelected = false
                                    } else {
                                        it.alPha = 0.5f
                                        it.isActiveToSwap = false
                                        it.isSelected = false
                                    }
                                }


                            } else if (type_loc_player == "defender") {

                                if (this.data?.get(1)?.size == 3) {
                                    list.map {
                                        if (it.type_loc_player == "defender") {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        }
                                    }
                                } else {

                                    list.map {
                                        if (it.type_loc_player == "goalkeeper") {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        }
                                    }
                                }


                            } else if (type_loc_player == "attacker") {

                                if (this.data?.get(3)?.size == 1) {
                                    list.map {
                                        if (it.type_loc_player == "attacker") {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        }
                                    }
                                } else {

                                    list.map {
                                        if (it.type_loc_player == "goalkeeper") {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        }
                                    }
                                }


                            } else if (type_loc_player == "line") {

                                if (this.data?.get(2)?.size == 2) {
                                    list.map {
                                        if (it.type_loc_player == "line") {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        }
                                    }
                                } else {

                                    list.map {
                                        if (it.type_loc_player == "goalkeeper") {
                                            it.alPha = 0.5f
                                            it.isActiveToSwap = false
                                            it.isSelected = false
                                        } else {
                                            it.alPha = 1.0f
                                            it.isActiveToSwap = true
                                            it.isSelected = false
                                        }
                                    }
                                }


                            } else {

                            }
                        }
                        else -> {
                            list.mapIndexed { _, player ->
                                player.alPha = 0.5f
                                player.isActiveToSwap = false
                                player.isSelected = false
                            }


                        }

                    }
                }
            else {
                this?.data?.mapIndexed { index, list ->
                    when (index) {

                        0 -> {
                            list.map { player ->
                                if (type_loc_player == "goalkeeper") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        1 -> {

                            list.mapIndexed { _, player ->
                                if (type_loc_player != "defender" && type_loc_player != "goalkeeper" && list.size > 3) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else if (type_loc_player == "defender") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        2 -> {
                            list.mapIndexed { _, player ->
                                if (type_loc_player != "line" && type_loc_player != "goalkeeper" && list.size > 2) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else if (type_loc_player == "line") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        3 -> {
                            list.mapIndexed { _, player ->
                                if (type_loc_player != "attacker" && type_loc_player != "goalkeeper" && list.size > 1) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else if (type_loc_player == "attacker") {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = false
                                } else {
                                    player.alPha = 0.5f
                                    player.isActiveToSwap = false
                                    player.isSelected = false
                                }
                            }
                        }
                        4 -> {
                            list.mapIndexed { playerIndex, player ->
                                if (parentPos == index && playerIndex == childPos) {
                                    player.alPha = 1.0f
                                    player.isActiveToSwap = true
                                    player.isSelected = true
                                } else if (type_loc_player != "goalkeeper") {
                                    if (player.type_loc_player == "goalkeeper") {
                                        player.alPha = 0.5f
                                        player.isActiveToSwap = false
                                        player.isSelected = false
                                    } else {
                                        player.alPha = 1.0f
                                        player.isActiveToSwap = true
                                        player.isSelected = false
                                    }

                                } else if (type_loc_player == "goalkeeper") {
                                    if (player.type_loc_player == "goalkeeper") {
                                        player.alPha = 1.0f
                                        player.isActiveToSwap = true
                                        player.isSelected = false
                                    } else {
                                        player.alPha = 0.5f
                                        player.isActiveToSwap = false
                                        player.isSelected = false
                                    }

                                }

                            }
                        }
                        else -> Unit
                    }
                }

            }


        })


        val substitutesList = MyTeamPlayersListLiveData.value?.data
        substitutesList?.let { data ->

            val updatedSubstitutesList = mutableListOf<MyteamPlayersResponse.Player>()

            data.forEach {
                it.forEach { player ->
                    if (!player.isSelected && player.isActiveToSwap) {
                        updatedSubstitutesList.add(player)
                    }
                }
            }

            Timber.e(updatedSubstitutesList.toString())

            this.substitutesList.value = updatedSubstitutesList


        }


    }

    fun resetActivePlayer() = resetActivePlayers()

    private fun resetActivePlayers() {
        MyTeamPlayersListLiveData.postValue(MyTeamPlayersListLiveData.value.apply {


            this?.data?.mapIndexed { _, list ->
                list.map { player ->
                    player.alPha = 1.0f
                    player.isActiveToSwap = false
                    player.isSelected = false
                }
            }
        })
    }


    //add direct change
    var validateChangeResultStateLiveData = SingleLiveEvent<AddDirectInsideChange>()
    // var validateChangePlayersList = myTeamPlayerUseCase.validateChangePlayersList()

    fun changePlayers(
        player_link_one: String,
        player_link_two: String
    ) = changePlayerss(player_link_one, player_link_two)

    private fun changePlayerss(
        player_link_one: String,
        player_link_two: String
    ) {
        repository.addDirectInsideChange(
            player_link_one,
            player_link_two
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->

                    MyTeamPlayersListLiveData.value = (MyTeamPlayersListLiveData.value.apply {
                        this?.data = data.data
                    })

                    validateChangeResultStateLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()

    }


    // add captain
    var addCaptainOrViseState = SingleLiveEvent<AddCaptainOrVise>()

    fun addCaptainOrViceCaptain(
        position: Int,
        parentPosition: Int,
        player_link: String,
        type: String
    ) {
        repository.addCaptainOrViceCaptain(
            player_link, type
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->

                    data?.let {

                        if (it.data?.okUpdate == 1) {

                            MyTeamPlayersListLiveData.postValue(MyTeamPlayersListLiveData.value.apply {

                                if (type == "captain") {

                                    this?.data?.mapIndexed { parentIndex, list ->
                                        list.mapIndexed { index, player ->
                                            when {
                                                parentIndex == parentPosition && index == position ->
                                                    player.type_key_coatch = "captain"
                                                player.type_key_coatch == "captain" ->
                                                    player.type_key_coatch = ""
                                                else -> player.type_key_coatch
                                            }
                                        }
                                    }

                                } else {
                                    this?.data?.mapIndexed { parentIndex, list ->
                                        list.mapIndexed { index, player ->
                                            when {
                                                parentIndex == parentPosition && index == position ->
                                                    player.type_key_coatch = "sub_captain"
                                                player.type_key_coatch == "sub_captain" ->
                                                    player.type_key_coatch = ""
                                                else -> player.type_key_coatch
                                            }
                                        }
                                    }

                                }


                            })
                        }
                    }



                    addCaptainOrViseState.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()

    }

}