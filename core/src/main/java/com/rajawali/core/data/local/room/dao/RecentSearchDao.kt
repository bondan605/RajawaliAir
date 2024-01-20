package com.rajawali.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM recentSearch")
    fun getRecentSearch() : List<RecentSearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearch(search: RecentSearchEntity) : Long
}