package com.sqli.guildes.data.models

import android.os.ParcelFileDescriptor
import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*



@Parcelize
@Entity(tableName = "contributions")
data class Contribution @JvmOverloads constructor (
    @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
    var id: String = UUID.randomUUID().toString(),
    var type: String,
    var descriptor: String = "",
    var points: Number = 0
) : Parcelable