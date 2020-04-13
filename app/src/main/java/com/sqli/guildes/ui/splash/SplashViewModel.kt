package com.sqli.guildes.ui.splash

import androidx.lifecycle.LiveData
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.SingleLiveEvent


class SplashViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _decideNextActivity = SingleLiveEvent<Int>()
    val decideNextActivity: LiveData<Int>
        get() = _decideNextActivity

    fun startSeeding() {
        val isAuth = dataManager.isAuthenticated()
        _decideNextActivity.postValue(isAuth)
    }

}
