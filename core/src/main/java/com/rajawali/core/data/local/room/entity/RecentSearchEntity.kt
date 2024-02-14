package com.rajawali.core.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentSearch")
data class RecentSearchEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "cityCode")
    val cityCode: String,

    @ColumnInfo(name = "airport")
    val airport: String,

    @ColumnInfo(name = "created")
    val created: Long,
)