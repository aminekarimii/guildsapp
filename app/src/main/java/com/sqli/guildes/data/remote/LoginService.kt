package com.sqli.guildes.data.remote

import com.sqli.guildes.data.remote.utils.ErrorResponse
import com.sqli.guildes.data.remote.utils.NetworkResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("users/login")
    fun login(@Body body : LoginRequest) : Single<NetworkResponse<LoginResponse, ErrorResponse>>
}