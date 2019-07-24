package com.sqli.guildes.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "guildes")
data class Guilde @JvmOverloads constructor(
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
        var id: String = UUID.randomUUID().toString(),
        var name: String,
        var representative: String,
        var points: Int
) : Parcelable