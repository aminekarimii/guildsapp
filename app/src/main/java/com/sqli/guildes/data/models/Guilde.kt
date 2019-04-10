package com.sqli.guildes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "guildes")
data class Guilde @JvmOverloads constructor(
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
        var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "points") var points: String,
        @ColumnInfo(name = "description") var description: String = ""
)