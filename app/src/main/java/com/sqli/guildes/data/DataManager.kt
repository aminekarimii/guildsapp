package com.sqli.guildes.data

import android.content.Context
import com.sqli.guildes.data.local.PreferencesHelper
import com.sqli.guildes.data.local.SharedPreferencesDelegate
import com.sqli.guildes.data.remote.ApiManager
import com.sqli.guildes.di.SingletonHolder

class DataManager (val context: Context) {

    companion object : SingletonHolder<DataManager, Context>(::DataManager)

    var apiManager : ApiManager = ApiManager(context)

    private var mPrefsHelper : PreferencesHelper = PreferencesHelper.getInstance(context)
    var tokenPref by SharedPreferencesDelegate(mPrefsHelper.mPref, PreferencesHelper.Constants.TOKEN, "")





}