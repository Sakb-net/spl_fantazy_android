package com.sakb.spl.ui.videos


import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.VideosResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.constants.Constants
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class VideosViewModel (private val repository: SplRepository) : BaseViewModel() {


    val videosLiveData = SingleLiveEvent<MutableList<VideosResponse.Data?>>()
    private var PAGE_NUMBER = 0

    init {
       // loadHmeData(PAGE_NUMBER)
    }

  fun  initLoading(){
      PAGE_NUMBER = 0
      loadHmeData()
    }

     fun loadMore(){
         PAGE_NUMBER++
         loadHmeData()
    }

     private fun loadHmeData() {
         Timber.e("PAGE_NUMBER = "+ PAGE_NUMBER)
        repository.videos(PAGE_NUMBER.toString(),
            Constants.PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    data.data?.let {
                        if (videosLiveData.value == null)
                            videosLiveData.value = it
                        else {
                            val temp = videosLiveData.value?.toMutableList()
                            temp?.addAll(it)
                            videosLiveData.value = temp
                        }
                    }

                },
                { throwable ->
                    handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
