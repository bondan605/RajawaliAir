package com.rajawali.core.data.local

import com.rajawali.core.data.local.room.dao.RecentSearchDao
import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImp(private val recentSearch: RecentSearchDao) : LocalRepository {
    override fun getRecentSearch(): List<RecentSearchEntity> =
        recentSearch.getRecentSearch()

    override fun addSearch(search: RecentSearchEntity): Boolean {
        val result = recentSearch.addSearch(search)
        return result >= 0L
    }
}