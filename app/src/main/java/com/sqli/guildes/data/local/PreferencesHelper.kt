package com.sqli.guildes.data.local

import android.content.Context
import android.content.SharedPreferences
import com.sqli.guildes.di.SingletonHolder


class PreferencesHelper(context: Context) {

    private val PREF_FILE_NAME = "guildes_app_pref_file"

    companion object : SingletonHolder<PreferencesHelper, Context>(::PreferencesHelper)

    val mPref: SharedPreferences


    init {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        mPref.edit().clear().apply()
    }


}
