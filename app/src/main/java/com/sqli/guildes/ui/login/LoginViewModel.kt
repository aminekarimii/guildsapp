package com.sqli.guildes.ui.login


import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.utils.SingleLiveEvent
import disposeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import log
import java.io.IOException
import java.util.concurrent.TimeoutException

class LoginViewModel (private var dataManager : DataManager) : ViewModel() {



    private val _requestToken = SingleLiveEvent<Resource<String>>()
    private val _message = MutableLiveData<String>()

    val requestToken: LiveData<Resource<String>> get() = _requestToken
    val message: LiveData<String> get() = _message

    private val compositeDisposable = CompositeDisposable()

    fun isUsernameAndPasswordValid(username : String, password : String) : Boolean
            = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)


    fun getRequestToken(username : String, password : String) {
        dataManager.apiManager.getRequestToken(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { tokenRes ->
                            _requestToken.postValue(tokenRes)
                            if (tokenRes is Resource.Success) {
                                dataManager.tokenPref = tokenRes.data
                            }
                        },
                        onError = { error -> handleError(error, "get-request-token")}
                )
                .disposeWith(compositeDisposable)
    }

    fun getToken() = dataManager.tokenPref

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
