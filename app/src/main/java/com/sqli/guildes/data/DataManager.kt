package com.sqli.guildes.data

import android.content.Context
import android.text.TextUtils
import com.sqli.guildes.R
import com.sqli.guildes.core.Constants
import com.sqli.guildes.core.Constants.ENDPOINT
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.local.PreferencesHelper
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.utils.SharedPreferencesDelegate
import com.sqli.guildes.data.models.User
import com.sqli.guildes.data.remote.LoginRequest
import com.sqli.guildes.data.remote.ApiService
import com.sqli.guildes.data.remote.utils.KotlinRxJava2CallAdapterFactory
import com.sqli.guildes.data.remote.utils.NetworkResponse
import com.sqli.guildes.di.SingletonHolder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class DataManager (val context: Context) {

    companion object : SingletonHolder<DataManager, Context>(::DataManager)


    private var apiService: ApiService = ApiService.makeService()

    private var prefsHelper : PreferencesHelper = PreferencesHelper.getInstance(context)

    var userIdPref by SharedPreferencesDelegate(prefsHelper.mPref, Constants.USER_ID, "")
    var usernamePref by SharedPreferencesDelegate(prefsHelper.mPref, Constants.USERNAME, "")
    var tokenPref by SharedPreferencesDelegate(prefsHelper.mPref, Constants.TOKEN, "")

    fun getRequestToken(username : String, password : String) : Single<Resource<String>> {
        return apiService.login(LoginRequest(username, password))
                .flatMap { response ->
                    Single.just(when (response) {
                        is NetworkResponse.Success -> {
                            userIdPref = response.body.userId
                            usernamePref = response.body.username
                            tokenPref = response.body.token
                            Resource.Success(response.body.token)
                        }
                        is NetworkResponse.ServerError -> {
                            Resource.Error<String>(response.body?.message ?: "Server Error")
                        }
                        is NetworkResponse.NetworkError -> {
                            Resource.Error(response.error.localizedMessage ?: "Network Error")
                        }
                    })
                }
    }

    fun isAuthenticated() : Boolean = !TextUtils.isEmpty(userIdPref)

    fun signOut() {
        //db.clear()
        prefsHelper.clear()
    }

    fun getCurrentUser() : Single<Resource<User>> =  apiService
            .getCurrentUserDetails("Bearer $tokenPref")
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        tokenPref = response.body.id
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<User>(response.body?.message ?: "Server Error")
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: "Network Error")
                    }
                })
            }



    fun getTopGuildes() : Single<Resource<List<Guilde>>> = apiService
            .getTopGuildes("Bearer $tokenPref")
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<List<Guilde>>(response.body?.message ?: "Server Error")
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: "Network Error")
                    }
                })
            }



    fun getGuildeDetails(guildeId : String) : Single<Resource<Guilde>> = apiService
            .getGuildeDetails("Bearer $tokenPref", guildeId)
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<Guilde>(response.body?.message ?: "Server Error")
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: "Network Error")
                    }
                })
            }

    fun getGuildeConstributors(guildeId : String) : Single<Resource<List<User>>> = apiService
            .getGuildeConstributors("Bearer $tokenPref", guildeId)
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<List<User>>(response.body?.message ?: "Server Error")
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: "Network Error")
                    }
                })
            }

}

