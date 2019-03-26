package com.sqli.guildes.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "guildes")
data class Guilde @JvmOverloads constructor(
        @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "description") var description: String = ""
)