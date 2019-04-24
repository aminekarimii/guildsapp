package com.sqli.guildes.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*


@Entity(tableName = "users",
        foreignKeys = [ForeignKey(
                entity = Guilde::class,
                parentColumns = ["id"],
                childColumns = ["guildeId"]
        )]
)

@Parcelize
data class User @JvmOverloads constructor (
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
        var id : String = UUID.randomUUID().toString(),
        var username: String = "akherbouch",
        var firstname: String = "admin",
        var lastname: String = "",
        var site: String = "Rabat",
        @field:Json(name="guilde_id") var guildeId: String = ""
) : Parcelable