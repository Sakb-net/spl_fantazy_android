package com.sakb.spl.ui.news

import com.sakb.spl.base.BaseViewModel
import com.sakb.spl.data.model.NewsResponse
import com.sakb.spl.data.repository.SplRepository
import com.sakb.spl.constants.Constants
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class NewsViewModel (private val repository: SplRepository) : BaseViewModel() {


    val newsLiveData = SingleLiveEvent<MutableList<NewsResponse.Data?>>()
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
        repository.news(PAGE_NUMBER.toString(), Constants.PAGE_LIMIT)
            .subscribeOn(Schedulers.io())
            .applyLoadingState()
            .subscribe(
                { data ->
                    data.data?.let {
                        if (newsLiveData.value == null)
                            newsLiveData.value = it.toMutableList()
                        else {
                            val temp = newsLiveData.value?.toMutableList()
                            temp?.addAll(it)
                            newsLiveData.value = temp
                        }
                    }

                },
                { throwable ->
                   handleApiException(throwable)
                }
            ).addToDisposableBag()
    }
}
