package com.sqli.guildes.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "guildes")
@Parcelize
data class Guilde @JvmOverloads constructor(
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
        var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "points") var points: Int,
        @ColumnInfo(name = "description") var description: String = ""
) : Parcelable