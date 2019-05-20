package com.sqli.guildes.ui.main

import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.ui.common.BackPressListener
import com.sqli.guildes.ui.common.SnackbarAction
import com.sqli.guildes.utils.ErrorUtil
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import log

class MainViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _snackbar = SingleLiveEvent<SnackbarAction>()
    val snackbar: LiveData<SnackbarAction>
        get() = _snackbar

    private val _toolbarTitle = SingleLiveEvent<String>()
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    private var _backPressListener = MutableLiveData<BackPressListener>()
    val backPressListener: LiveData<BackPressListener>
        get() = _backPressListener

    val currentUser : User?
        get() = dataManager.currentUserPref

    private var _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean>
        get() = _isAuthenticated

    fun checkAuthentication() {
        dataManager.getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onSuccess = {
                            _isAuthenticated.postValue(it is Resource.Success)
                        },
                        onError = {_isAuthenticated.postValue(false) }
                )
                .disposeWith(compositeDisposable)
    }


    fun showSnackbar(@StringRes message: Int) {
        _snackbar.postValue(SnackbarAction(message))
    }

    fun showSnackbar(@StringRes message: Int, @StringRes actionText: Int, clickListener: View.OnClickListener) {
        _snackbar.postValue(SnackbarAction(
                message = message,
                actionText = actionText,
                action = clickListener
        ))
    }

    fun showSnackbar(@StringRes message: Int, @StringRes actionText: Int, length: Int, clickListener: View.OnClickListener) {
        _snackbar.postValue(SnackbarAction(
                message = message,
                actionText = actionText,
                length = length,
                action = clickListener
        ))
    }

    fun updateToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun setBackPressListener(listener: BackPressListener?) = _backPressListener.postValue(listener)

}
