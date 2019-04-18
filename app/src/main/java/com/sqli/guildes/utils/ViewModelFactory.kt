package com.sqli.guildes.utils

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.login.LoginViewModel
import com.sqli.guildes.ui.splash.SplashViewModel


class ViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(LoginViewModel::class.java) ->
                        LoginViewModel(dataManager)
                    isAssignableFrom(SplashViewModel::class.java) ->
                        SplashViewModel(dataManager)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T


    //Singleton
    companion object {

        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE
                        ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE
                            ?: ViewModelFactory(
                                    DataManager.getInstance(application.applicationContext))
                            .also { INSTANCE = it }
                }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}