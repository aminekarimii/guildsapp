package com.sqli.guildes.ui.splash

import androidx.lifecycle.LiveData
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.SingleLiveEvent


class SplashViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _decideNextActivity = SingleLiveEvent<Boolean>()
    val decideNextActivity: LiveData<Boolean>
        get() = _decideNextActivity

    private val _isAdmin = SingleLiveEvent<Boolean>()
    val isAdmin: LiveData<Boolean>
        get() = _isAdmin

    fun startSeeding() {
        val isAuth = dataManager.isAuthenticated()
        _decideNextActivity.postValue(isAuth)
        if (isAuth)
            isAdminCheck()
    }

    private fun isAdminCheck() = _isAdmin.postValue(dataManager.isAdmin())


}
