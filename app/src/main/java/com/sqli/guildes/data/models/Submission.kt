package com.sqli.guildes.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "submissions")
data class Submission @JvmOverloads constructor(
        @PrimaryKey @ColumnInfo(name = "id") @field:Json(name="_id")
        var id: String = UUID.randomUUID().toString(),
        var subject : String,
        var type :String,
        var description :String,
        var points :Int,
        //Not found on API:
        //@Embedded val contribution: Contribution,
        @Embedded val createdBy: User,
        var createdAt: String,
        var updatedAt: String
) : Parcelable