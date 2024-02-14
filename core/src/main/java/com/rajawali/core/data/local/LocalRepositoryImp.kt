package com.rajawali.core.data.local

import com.rajawali.core.data.local.room.dao.RecentSearchDao
import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.domain.repository.LocalRepository
import timber.log.Timber

class LocalRepositoryImp(private val recentSearch: RecentSearchDao) : LocalRepository {
    override suspend fun getRecentSearch(): List<RecentSearchEntity> =
        recentSearch.getRecentSearch()


    override suspend fun addSearch(search: RecentSearchEntity) {
        try {
            val entity = recentSearch.addSearch(search)

            if (entity < 0L)
                throw Exception(entity.toString())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    override suspend fun clearRecentSearch() {
        recentSearch.clearRecentSearch()
    }
}