package com.sqli.guildes.core

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.addsubmission.AddSubmissionViewModel
import com.sqli.guildes.ui.admin.AdminViewModel
import com.sqli.guildes.ui.guildedetail.GuildeDetailsViewModel
import com.sqli.guildes.ui.home.HomeViewModel
import com.sqli.guildes.ui.login.LoginViewModel
import com.sqli.guildes.ui.main.MainViewModel
import com.sqli.guildes.ui.myguild.MyGuildViewModel
import com.sqli.guildes.ui.profile.ProfileViewModel
import com.sqli.guildes.ui.splash.SplashViewModel
import com.sqli.guildes.ui.usersubmissions.UserSubmissionsViewModel
import com.sqli.guildes.ui.validation.ValidationViewModel


class ViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(LoginViewModel::class.java) ->
                        LoginViewModel(dataManager)
                    isAssignableFrom(SplashViewModel::class.java) ->
                        SplashViewModel(dataManager)
                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(dataManager)
                    isAssignableFrom(HomeViewModel::class.java) ->
                        HomeViewModel(dataManager)
                    isAssignableFrom(GuildeDetailsViewModel::class.java) ->
                        GuildeDetailsViewModel(dataManager)
                    isAssignableFrom(ProfileViewModel::class.java) ->
                        ProfileViewModel(dataManager)
                    isAssignableFrom(AddSubmissionViewModel::class.java) ->
                        AddSubmissionViewModel(dataManager)
                    isAssignableFrom(UserSubmissionsViewModel::class.java) ->
                        UserSubmissionsViewModel(dataManager)
                    isAssignableFrom(AdminViewModel::class.java) ->
                        AdminViewModel(dataManager)
                    isAssignableFrom(ValidationViewModel::class.java) ->
                        ValidationViewModel(dataManager)
                    isAssignableFrom(MyGuildViewModel::class.java) ->
                        MyGuildViewModel(dataManager)
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