package com.sqli.guildes.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sqli.guildes.data.DataManager


class LoginViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}