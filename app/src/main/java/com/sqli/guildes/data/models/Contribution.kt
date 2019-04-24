package com.sqli.guildes.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*


@Entity(tableName = "contributions",
        foreignKeys = [ForeignKey(
                entity = User::class,
                parentColumns = ["id"],
                childColumns = ["userId"]
        )]
)

@Parcelize
data class Contribution @JvmOverloads constructor (
    @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
    var id: String = UUID.randomUUID().toString(),
    var subject : String = "",
    var type : String = "",
    var date : String = "",
    var points : Number = 0,
    var validated : Boolean = false,
    @field:Json(name="user_id") var userId : String
) : Parcelable
