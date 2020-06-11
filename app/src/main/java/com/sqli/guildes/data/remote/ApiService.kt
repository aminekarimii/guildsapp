package com.sqli.guildes.data.remote

import com.sqli.guildes.core.Constants
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.data.models.User
import com.sqli.guildes.data.remote.utils.ErrorResponse
import com.sqli.guildes.data.remote.utils.KotlinRxJava2CallAdapterFactory
import com.sqli.guildes.data.remote.utils.NetworkResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST("users/login")
    fun login(@Body body: LoginRequest)
            : Single<NetworkResponse<LoginResponse, ErrorResponse>>

    @GET("users/current")
    fun getCurrentUserDetails(@Header("Authorization") authHeader: String)
            : Single<NetworkResponse<User, ErrorResponse>>

    @GET("users/find/{userId}")
    fun getUserById(@Header("Authorization") authHeader: String, @Path("userId") userId: String)
            : Single<NetworkResponse<User, ErrorResponse>>

    @GET("users/guilde/{id}")
    fun getGuildeConstributors(@Header("Authorization") authHeader: String,
                               @Path("id") guildeId: String)
            : Single<NetworkResponse<List<User>, ErrorResponse>>

    @GET("guildes/current")
    fun getCurrentGuildeDetails(@Header("Authorization") authHeader: String)
            : Single<NetworkResponse<Guilde, ErrorResponse>>

    @GET("guildes/details/{id}")
    fun getGuildeDetails(@Header("Authorization") authHeader: String,
                         @Path("id") guildeId: String)
            : Single<NetworkResponse<Guilde, ErrorResponse>>

    @GET("guildes/top")
    fun getTopGuildes(@Header("Authorization") authHeader: String)
            : Single<NetworkResponse<List<Guilde>, ErrorResponse>>

    @GET("submissions/me")
    fun getCurrentUserSubmissions(@Header("Authorization") authHeader: String)
            : Single<NetworkResponse<List<Submission>, ErrorResponse>>

    @GET("submissions/user/{id}")
    fun getUserSubmissions(@Header("Authorization") authHeader: String, @Path("id") id: String)
            : Single<NetworkResponse<List<Submission>, ErrorResponse>>

    @GET("submissions/validate/{id}")
    fun validate(@Header("Authorization") authHeader: String, @Path("id") id: String)
            : Single<NetworkResponse<Submission, ErrorResponse>>

    @GET("submissions/details/{id}")
    fun getSubmissions(@Header("Authorization") authHeader: String, @Path("id") id: String)
            : Single<NetworkResponse<Submission, ErrorResponse>>

    @GET("submissions/all")
    fun getAllSubmissions(@Header("Authorization") authHeader: String)
            : Single<NetworkResponse<List<Submission>, ErrorResponse>>

    @GET("submissions/all/{id}")
    fun getGuildSubmissions(@Path("id") id: String, @Header("Authorization") authHeader: String)
            : Single<NetworkResponse<List<Submission>, ErrorResponse>>

    @POST("submissions/add")
    fun addSubmission(@Header("Authorization") authHeader: String, @Body submission: AddSubmissionRequest)
            : Single<NetworkResponse<AddSubmissionResponse, ErrorResponse>>


    companion object {
        fun makeService(): ApiService {
            return Retrofit.Builder()
                    .baseUrl(Constants.ENDPOINT)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(KotlinRxJava2CallAdapterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ApiService::class.java)
        }
    }

}