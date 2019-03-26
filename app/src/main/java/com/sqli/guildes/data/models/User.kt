package com.sqli.guildes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "users",
        foreignKeys = [ ForeignKey(
                entity = Guilde::class,
                parentColumns = ["id"],
                childColumns = ["guildeId"]
        )]
)

data class User @JvmOverloads constructor (
        @PrimaryKey @ColumnInfo(name = "username") var username: String = "akherbouch",
        @ColumnInfo(name = "firstname") var firstname: String = "admin",
        @ColumnInfo(name = "lastname") var lastname: String = "",
        @ColumnInfo(name = "site") var site: String = "Rabat",
        @ColumnInfo(name = "guildeId") var guildeId: String = ""
)