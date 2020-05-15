package com.sakb.spl.ui.editprofile

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.GetTeamResponse
import com.sakb.spl.data.model.ProfileResponse
import com.sakb.spl.data.model.UploadImgResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers

/**
 * Created by dev.mahmoud_ashraf on 10/8/2019.
 */

class EditProfileViewModel(private val repository: SplRepository) : BaseViewModel() {

    var newImageUrl: String? = null
    var selectedTeamPosition: Int? = null
    var teamsNames = mutableListOf<String>()

    var teams: MutableList<GetTeamResponse.Data?>? = mutableListOf()

    var teamsResultLiveData = SingleLiveEvent<GetTeamResponse>()
    private fun getTeams() {
        repository.getTeams()
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    teamsResultLiveData.value = data

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }

    // load profile data
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


    // update profile
    fun updateProfile(
       best_team: String, display_name: String,
        email: String,
        image: String
    ) {

        repository.UpdateProfile(
            best_team = best_team,
            display_name = display_name,
            email = email,
            image = image
        )
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    showSuccessMessage(data?.Message?:"")
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }


    var uploadingImage = SingleLiveEvent<UploadImgResponse>()


    fun ChangeImage(
        image: String
    ) {

        repository.uploadImage( image)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    uploadingImage.value = data
                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }












    init {
        getTeams()
    }


    fun updateTeamsList(data: MutableList<GetTeamResponse.Data?>?) {
        this.teams = data
        mapTeamsToListOfString(teams)
    }

    private fun mapTeamsToListOfString(teams: MutableList<GetTeamResponse.Data?>?) {
        teamsNames.clear()
        teams?.map {
            it?.let {
                it.name?.let { name -> teamsNames.add(name) }
            }

        }

    }


}