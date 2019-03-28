package com.sqli.guildes.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.login.LoginViewModel
import com.sqli.guildes.ui.splash.SplashViewModel


class BaseViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataManager) as T
        }
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(dataManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}