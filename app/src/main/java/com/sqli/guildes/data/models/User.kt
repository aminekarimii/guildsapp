package com.sqli.guildes.data.models

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "users")
data class User @JvmOverloads constructor (
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
        var id : String = UUID.randomUUID().toString(),
        var username: String = "admin",
        var firstname: String = "",
        var lastname: String = "",
        var site: String = "Rabat",
        @field:Json(name="hired_date") var hiredDate: String = "",
        var isAdmin: Boolean = false,
        @Embedded var guilde : Guilde
) : Parcelable