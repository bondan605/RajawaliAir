package com.rajawali.core.domain.repository

import com.rajawali.core.data.local.room.entity.RecentSearchEntity

interface LocalRepository {

    suspend fun getRecentSearch(): List<RecentSearchEntity>

    suspend fun addSearch(search: RecentSearchEntity)

    suspend fun clearRecentSearch()

}
