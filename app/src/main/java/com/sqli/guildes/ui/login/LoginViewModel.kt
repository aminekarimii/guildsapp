package com.sqli.guildes.ui.login


import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil.handleError
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

class LoginViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _isAuthenticated = SingleLiveEvent<Boolean>()
    val isAuthenticated: LiveData<Boolean>
        get() = _isAuthenticated

    private val _isAdmin = SingleLiveEvent<Boolean>()
    val isAdmin: LiveData<Boolean>
        get() = _isAdmin

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String>
        get() = _message


    fun getRequestToken(username: String, password: String) {
        if (!isUsernameAndPasswordValid(username, password))
            return _message.postValue("Please provide a valid email and password.")

        dataManager.getRequestToken(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterSuccess {
                    getCurrentUser()
                }
                .subscribeBy(
                        onSuccess = {
                            when (it) {
                                is Resource.Success -> {
                                    _isAuthenticated.postValue(true)
                                }
                                is Resource.Error -> _message.postValue(it.errorMessage)
                            }
                        },
                        onError = {
                            val err = handleError(it, "get-request-token")
                            _message.postValue(err)
                        }
                )
                .disposeWith(compositeDisposable)

    }

    fun getCurrentUser(){
        dataManager.getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            when (it) {
                                is Resource.Success -> _isAdmin.postValue(it.data.isAdmin)
                                is Resource.Error -> _message.postValue(it.errorMessage)
                            }
                        },
                        onError = { _isAuthenticated.postValue(false) }
                )
                .disposeWith(compositeDisposable)
    }

    private fun isUsernameAndPasswordValid(username: String, password: String): Boolean =
            !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)


}
