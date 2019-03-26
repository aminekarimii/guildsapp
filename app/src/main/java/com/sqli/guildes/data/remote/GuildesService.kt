package com.sqli.guildes.data.remote

import com.sqli.guildes.data.remote.requests.LoginRequest
import com.sqli.guildes.data.remote.responses.LoginResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface GuildesService {

    companion object { val ENDPOINT = "http://10.242.2.190:3000/api/" }

    @POST("users/login")
    fun login(@Body body : LoginRequest) : Observable<LoginResponse>

    object Creator {
        fun newGitHubService(): GuildesService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(GuildesService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //https://medium.com/mindorks/rxandroid-retrofit-2fff4f89fa85
                    .build()
            return retrofit.create(GuildesService::class.java!!)
        }
    }
}