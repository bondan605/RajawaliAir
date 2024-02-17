package com.rajawali.core.domain.repository

import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun getRecentSearch(): List<RecentSearchEntity>

    suspend fun addSearch(search: RecentSearchEntity)

    suspend fun clearRecentSearch()

    suspend fun isLogin() : String

    suspend fun isAccessToken() : String

    fun logout() : Flow<Boolean>

    fun createLoginSession(accessToken: String, id: String): Flow<Boolean>
}
