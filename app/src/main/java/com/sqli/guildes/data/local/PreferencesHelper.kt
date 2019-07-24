package com.sqli.guildes.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.sqli.guildes.core.Constants.USER
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.User
import com.sqli.guildes.di.SingletonHolder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlin.reflect.KProperty


class PreferencesHelper(context: Context) {

    private val APP_PREFERENCES = "guildes_app_pref_file"

    companion object : SingletonHolder<PreferencesHelper, Context>(::PreferencesHelper)

    private val prefs: SharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
    private val moshi: Moshi = Moshi.Builder().build()
    private val userAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)

    fun clear() = prefs.edit().clear().apply()



    inner class UserPrefDelegate {

        operator fun getValue(thisRef: Any?, property: KProperty<*>): User? = prefs.getString(USER, null)?.let {
            userAdapter.fromJson(it)
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, user: User?) = userAdapter.toJson(user).also {
            prefs.edit().putString(USER, it).apply()
        }
    }


    inner class SharedPreferencesDelegate<T>(
            private val key: String,
            private val defaultValue: T
    ) {

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return findPreferences(key, defaultValue)
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            savePreference(key, value)
        }

        @Suppress("UNCHECKED_CAST")
        private fun findPreferences(key: String, defaultValue: T): T {
            with(prefs)
            {
                val result: Any = when (defaultValue) {
                    is Boolean -> getBoolean(key, defaultValue)
                    is Int -> getInt(key, defaultValue)
                    is Long -> getLong(key, defaultValue)
                    is Float -> getFloat(key, defaultValue)
                    is String -> getString(key, defaultValue)
                    else -> throw IllegalArgumentException()
                }
                return result as T
            }
        }

        @SuppressLint("CommitPrefEdits")
        private fun savePreference(key: String, value: T) {
            with(prefs.edit())
            {
                when (value) {
                    is Boolean -> putBoolean(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                    is String -> putString(key, value)
                    else -> throw IllegalArgumentException()
                }.apply()
            }
        }
    }
}
