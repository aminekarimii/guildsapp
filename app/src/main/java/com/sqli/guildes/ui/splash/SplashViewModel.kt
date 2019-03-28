package com.sqli.guildes.ui.splash


import androidx.lifecycle.ViewModel
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import log


class SplashViewModel (dataManager : DataManager) : BaseViewModel<SplashNavigator>(dataManager) {


    fun decideNextActivity() {
        if(dataManager.isAuthenticated())
            return navigator.openMainActivity()

         return navigator.openLoginActivity()
    }


}
