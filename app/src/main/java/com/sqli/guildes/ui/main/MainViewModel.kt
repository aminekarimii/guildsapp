package com.sqli.guildes.ui.main

import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.ui.common.BackPressListener
import com.sqli.guildes.ui.common.SnackbarAction
import com.sqli.guildes.utils.SharedPreferencesDelegate
import com.sqli.guildes.utils.SingleLiveEvent

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
