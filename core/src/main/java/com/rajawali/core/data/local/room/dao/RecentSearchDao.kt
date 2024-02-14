package com.rajawali.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajawali.core.data.local.room.entity.RecentSearchEntity

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recentSearch ORDER BY created DESC LIMIT 5")
    suspend fun getRecentSearch(): List<RecentSearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(search: RecentSearchEntity): Long

    @Query("DELETE FROM recentSearch")
    suspend fun clearRecentSearch()
}