package com.sqli.guildes.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.SingleLiveEvent
import log


class SplashViewModel (dataManager : DataManager) : BaseViewModel(dataManager) {

    private val _decideNextActivity = SingleLiveEvent<Boolean>()
    val decideNextActivity: LiveData<Boolean>
        get() = _decideNextActivity


    fun startSeeding() = _decideNextActivity.postValue(dataManager.isAuthenticated())


}
