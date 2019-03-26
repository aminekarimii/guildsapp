package com.sqli.guildes.data.local

import android.content.Context
import android.content.SharedPreferences
import com.sqli.guildes.di.SingletonHolder


class PreferencesHelper(context: Context) {

    private val PREF_FILE_NAME = "guildes_app_pref_file"

    private val USER_ID = "_id"
    private val USERNAME = "username"
    private val TOKEN = "token"

    companion object : SingletonHolder<PreferencesHelper, Context>(::PreferencesHelper)

    private val mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        mPref.edit().clear().apply()
    }

    fun putUserId(id: String) = mPref.edit().putString(USER_ID, id).apply()
    fun getUserId(): String? = mPref.getString(USER_ID, null)

    fun putUsername(username: String) = mPref.edit().putString(USERNAME, username).apply()
    fun getUsername(): String? = mPref.getString(USERNAME, null)

    fun putToken(token: String) = mPref.edit().putString(TOKEN, token).apply()
    fun getToken(): String? = mPref.getString(TOKEN, null)


}
