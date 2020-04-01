package com.sqli.guildes.data.remote

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginRequest(
        @field:Json(name = "username") val username: String,
        @field:Json(name = "password") val password: String
) : Parcelable

@Parcelize
data class AddSubmissionRequest(
        @field:Json(name = "subject") val subject: String,
        @field:Json(name = "type") val type: String,
        @field:Json(name = "description") val description: String,
        @field:Json(name = "points") val points: Int
) : Parcelable