package com.sqli.guildes.data

import android.content.Context
import android.text.TextUtils
import com.sqli.guildes.core.Constants
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.local.PreferencesHelper
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.data.models.User
import com.sqli.guildes.data.remote.AddSubmissionRequest
import com.sqli.guildes.data.remote.ApiService
import com.sqli.guildes.data.remote.LoginRequest
import com.sqli.guildes.data.remote.utils.NetworkResponse
import com.sqli.guildes.di.SingletonHolder
import io.reactivex.Single

class DataManager(val context: Context) {

    companion object : SingletonHolder<DataManager, Context>(::DataManager)

    private val SERVER_ERROR_MSG = "Server Error"
    private val NETWORK_ERROR_MSG = "Network Error"
    private var apiService: ApiService = ApiService.makeService()
    private var prefsHelper: PreferencesHelper = PreferencesHelper.getInstance(context.applicationContext)

    var tokenPref by prefsHelper.SharedPreferencesDelegate(Constants.TOKEN, "")
    var currentUserPref by prefsHelper.UserPrefDelegate()

    fun getRequestToken(username: String, password: String): Single<Resource<String>> {
        return apiService.login(LoginRequest(username, password))
                .flatMap { response ->
                    Single.just(when (response) {
                        is NetworkResponse.Success -> {
                            tokenPref = response.body.token
                            Resource.Success(response.body.token)
                        }
                        is NetworkResponse.ServerError -> {
                            Resource.Error<String>(response.body?.message ?: SERVER_ERROR_MSG)
                        }
                        is NetworkResponse.NetworkError -> {
                            Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                        }
                    })
                }
    }

    fun isAuthenticated(): Boolean = !TextUtils.isEmpty(tokenPref)

    fun signOut() {
        //db.clear()
        prefsHelper.clear()
    }

    fun getCurrentUser(): Single<Resource<User>> = apiService
            .getCurrentUserDetails("Bearer $tokenPref")
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        currentUserPref = response.body
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<User>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                })
            }

    fun getUserById(userId: String): Single<Resource<User>> = apiService
            .getUserById("Bearer $tokenPref", userId)
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<User>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                })
            }

    fun getTopGuildes(): Single<Resource<List<Guilde>>> = apiService
            .getTopGuildes("Bearer $tokenPref")
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<List<Guilde>>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                })
            }


    fun getGuildeDetails(guildeId: String): Single<Resource<Guilde>> = apiService
            .getGuildeDetails("Bearer $tokenPref", guildeId)
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<Guilde>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                })
            }

    fun getGuildeConstributors(guildeId: String): Single<Resource<List<User>>> = apiService
            .getGuildeConstributors("Bearer $tokenPref", guildeId)
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<List<User>>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                })
            }

    fun getSubmissionsCurrentUser(): Single<Resource<List<Submission>>> = apiService
            .getCurrentUserSubmissions("Bearer $tokenPref")
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<List<Submission>>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                })
            }

    fun getUserContributions(userId: String): Single<Resource<List<Submission>>> = apiService
            .getUserSubmissions("Bearer $tokenPref", userId)
            .flatMap { response ->
                Single.just(when (response) {
                    is NetworkResponse.Success -> {
                        Resource.Success(response.body)
                    }
                    is NetworkResponse.ServerError -> {
                        Resource.Error<List<Submission>>(response.body?.message ?: SERVER_ERROR_MSG)
                    }
                    is NetworkResponse.NetworkError -> {
                        Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                    }
                })
            }

    fun postSubmission(subject: String, description: String, type: String, points: Int): Single<Resource<String>> {
        return apiService.addSubmission("Bearer $tokenPref",
                        AddSubmissionRequest(subject, type, description, points))
                .flatMap { response ->
                    Single.just(when (response) {
                        is NetworkResponse.Success -> {
                            Resource.Success(response.body.createdAt)
                        }
                        is NetworkResponse.ServerError -> {
                            Resource.Error<String>(response.body?.message ?: SERVER_ERROR_MSG)
                        }
                        is NetworkResponse.NetworkError -> {
                            Resource.Error(response.error.localizedMessage ?: NETWORK_ERROR_MSG)
                        }
                    })
                }
    }

}

