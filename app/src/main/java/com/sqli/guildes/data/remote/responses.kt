package com.sqli.guildes.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse (
        @field:Json(name="_id") val userId: String,
        @field:Json(name="username") val username: String,
        @field:Json(name="token") val token: String
): Parcelable