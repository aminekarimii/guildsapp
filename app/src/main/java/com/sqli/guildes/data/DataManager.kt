package com.sqli.guildes.data

import android.content.Context
import com.sqli.guildes.data.local.PreferencesHelper
import com.sqli.guildes.data.remote.GuildesService
import com.sqli.guildes.data.remote.requests.LoginRequest
import com.sqli.guildes.data.remote.responses.LoginResponse
import com.sqli.guildes.di.SingletonHolder
import io.reactivex.Observable

class DataManager (val context: Context) {

    companion object : SingletonHolder<PreferencesHelper, Context>(::PreferencesHelper)

    var mService : GuildesService
    var mPrefsHelper : PreferencesHelper

    init {
        mService = GuildesService.Creator.newGitHubService()
        mPrefsHelper = PreferencesHelper.getInstance(context)
    }

    fun login(username : String, password : String) : Observable<LoginResponse> {
        return mService.login(LoginRequest())
    }


}