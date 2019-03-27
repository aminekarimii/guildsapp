package com.sqli.guildes.data.remote

import android.content.Context
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.remote.utils.KotlinRxJava2CallAdapterFactory
import com.sqli.guildes.data.remote.utils.NetworkResponse
import com.sqli.guildes.di.SingletonHolder
import io.reactivex.Single

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiManager(val context: Context) {

    companion object : SingletonHolder<ApiManager, Context>(::ApiManager)

    private val ENDPOINT = "http://10.242.2.190:3000/api/"

    private var loginService: LoginService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(KotlinRxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //https://medium.com/mindorks/rxandroid-retrofit-2fff4f89fa85
                .build()

        loginService = retrofit.create(LoginService::class.java)
    }

    fun getRequestToken(username : String, password : String) : Single<Resource<String>> {
        return loginService.login(LoginRequest(username, password))
                .flatMap { response ->
                    Single.just(when (response) {
                        is NetworkResponse.Success -> {
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

}
