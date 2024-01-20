package com.rajawali.core.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rajawali.core.data.local.room.dao.RecentSearchDao
import com.rajawali.core.data.local.room.entity.RecentSearchEntity


@Database(
    entities = [RecentSearchDao::class],
    version = 1,
    exportSchema = false
)
abstract class RajawaliDatabase: RoomDatabase() {

    abstract fun recentSearchDao(): RecentSearchDao
}