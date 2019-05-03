package com.sqli.guildes.data.remote

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.sqli.guildes.core.Constants
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.data.models.User
import com.sqli.guildes.data.remote.utils.ErrorResponse
import com.sqli.guildes.data.remote.utils.KotlinRxJava2CallAdapterFactory
import com.sqli.guildes.data.remote.utils.NetworkResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface ApiService {

    @POST("users/login")
    fun login(@Body body : LoginRequest)
            : Single<NetworkResponse<LoginResponse, ErrorResponse>>

    @GET("users/current")
    fun getCurrentUserDetails(@Header("Authorization") authHeader : String)
            : Single<NetworkResponse<User, ErrorResponse>>

    @GET("users/guilde/{id}")
    fun getGuildeConstributors(@Header("Authorization") authHeader : String,
                               @Path("id") guildeId : String)
            : Single<NetworkResponse<List<User>, ErrorResponse>>

    @GET("guildes/current")
    fun getCurrentGuildeDetails(@Header("Authorization") authHeader : String)
            : Single<NetworkResponse<Guilde, ErrorResponse>>

    @GET("guildes/details/{id}")
    fun getGuildeDetails(@Header("Authorization") authHeader : String,
                          @Path("id") guildeId : String)
            : Single<NetworkResponse<Guilde, ErrorResponse>>

    @GET("guildes/top")
    fun getTopGuildes(@Header("Authorization") authHeader : String)
            : Single<NetworkResponse<List<Guilde>, ErrorResponse>>


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