package com.sakb.spl.base

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sakb.spl.data.model.ErrorResponse
import com.sakb.spl.data.model.PlayerMasterResponse
import com.sakb.spl.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val loadingState = SingleLiveEvent<Boolean>()
    val successEvent = SingleLiveEvent<String>()
    val errorEvent = SingleLiveEvent<String?>()
    val noConnectionErrorEvent = SingleLiveEvent<Boolean>()
    val unAuthorizedErrorEvent = SingleLiveEvent<Boolean>()

    protected fun Disposable.addToDisposableBag() {
        compositeDisposable.add(this)
    }

    protected fun showLoading() {
        loadingState.value = true
    }

    protected fun hideLoading() {
        loadingState.value = false
    }

    protected fun showSuccessMessage(message: String) {
        successEvent.value = message
    }

    protected fun showErrorMessage(message: String?) {
        errorEvent.value = message
    }

    // generic function to handle error
    protected fun handleApiException(throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                handleNoConnectionError()
            }
            is HttpException -> {
                when (throwable.code()) {
                    400 -> {
                        val errorResponse = Gson().fromJson(
                            throwable.response()?.errorBody()?.string(),
                            ErrorResponse::class.java
                        )
                        showErrorMessage(errorResponse.Message)
                    }
                    401 -> {
                        handleUnAuthorizedError()
                    }
                    else -> {
                        showErrorMessage(throwable.localizedMessage)
                    }
                }
            }
            else -> {
                showErrorMessage(throwable.localizedMessage)
            }
        }
    }

    private fun handleUnAuthorizedError() {
        unAuthorizedErrorEvent.value = true
    }

    private fun handleNoConnectionError() {
        noConnectionErrorEvent.value = true
    }


    protected fun <T> Single<T>.applyLoadingState(): Single<T> =
        this.observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }


    override fun onCleared() {
        compositeDisposable.dispose()
    }
}