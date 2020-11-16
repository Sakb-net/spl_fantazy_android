package com.sakb.spl.ui.manageleague

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.*
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

class ManageLeagueViewModel(private val repository: SplRepository) : BaseViewModel() {
    val allSubeldawry = SingleLiveEvent<GroupSubEldawryResponse>()
    fun loadAllSubeldawry(typeLeague: String, linkLeague: String) {
        repository.getGroupSubEldawry(typeLeague, linkLeague)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    allSubeldawry.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    val settingGroupsResponse = SingleLiveEvent<SettingGroupsResponse>()
    fun loadSettingGroup(typeLeague: String, linkLeague: String) {
        repository.getSettingsGroupsEldawry(type_league = typeLeague, link_league = linkLeague)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    settingGroupsResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val updateGroupResponse = SingleLiveEvent<UpdateGroupResponse>()
    fun loadUpdateGroup(typeLeague: String, linkLeague: String, linkSub: String, name: String) {
        repository.putUpdateGroupEldawry(typeLeague, linkLeague, linkSub, name)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    updateGroupResponse.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val deleteGroup = SingleLiveEvent<BaseResponse>()
    fun loadDeleteGroup(typeLeague: String, linkLeague: String) {
        repository.deleteGroupEldawry(typeLeague, linkLeague).subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    deleteGroup.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val switchAdmin = SingleLiveEvent<BaseResponse>()
    fun loadSwitchAdmin(typeLeague: String, linkLeague: String, user_name: String) {
        repository.switchAdminGroup(typeLeague, linkLeague, user_name).subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    switchAdmin.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    val deletePlayer = SingleLiveEvent<DeletePlayerResponse>()
    fun loadDeletePlayer(typeLeague: String, linkLeague: String, user_name: String) {
        repository.deletePlayer(typeLeague, linkLeague, user_name).subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    deletePlayer.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}