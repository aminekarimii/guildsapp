package com.sqli.guildes.data.remote.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorResponse( val status: String, val message: String): Parcelable