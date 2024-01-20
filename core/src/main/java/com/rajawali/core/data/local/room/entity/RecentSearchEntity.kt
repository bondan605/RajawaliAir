package com.rajawali.core.data.local.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentSearch")
data class RecentSearchEntity(

    @ColumnInfo(name = "id")
    val id : String,

    @ColumnInfo(name = "city")
    val city : String,

    @ColumnInfo(name = "cityCode")
    val cityCode : String,

    @ColumnInfo(name = "airport")
    val airport : String,
    )