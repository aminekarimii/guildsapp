package com.sqli.guildes.ui.login


import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.SingleLiveEvent
import com.sqli.guildes.core.extensions.disposeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import log
import java.io.IOException
import java.util.concurrent.TimeoutException

class LoginViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _requestToken = SingleLiveEvent<Resource<String>>()
    val requestToken: LiveData<Resource<String>>
        get() = _requestToken

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String>
        get() = _message


    private fun isUsernameAndPasswordValid(username : String, password : String) : Boolean
            = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)


    fun getRequestToken(username : String, password : String) {
        if(!isUsernameAndPasswordValid(username, password))
            return _message.postValue("Please provide a valid email and password.")

        dataManager.getRequestToken(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { when (it) {
                            is Resource.Success -> {
                                _requestToken.postValue(it)
                            }
                            is Resource.Error -> {
                                //navigator.handleError("An error occurred while trying to login")
                                _message.postValue(it.errorMessage)
                            }
                        }},
                        onError = { error ->
                            handleError(error, "get-request-token")
                        }
                )
                .disposeWith(compositeDisposable)
    }

    private fun handleError(error: Throwable, caller: String) {
        error.localizedMessage?.let {
            log("ERROR $caller -> $it")
        } ?: log("ERROR $caller ->")
                .also {
                    error.printStackTrace()
                }
        when (error) {
            is IOException -> _message.postValue("Please check your internet connection")
            is TimeoutException -> _message.postValue("Request timed out")
            else -> _message.postValue("An error occurred")
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}
