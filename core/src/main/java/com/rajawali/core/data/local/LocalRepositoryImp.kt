package com.rajawali.core.data.local

import com.rajawali.core.data.local.dataStorePreference.DataStore
import com.rajawali.core.data.local.room.dao.RecentSearchDao
import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class LocalRepositoryImp(
    private val recentSearch: RecentSearchDao,
    private val userDataStore: DataStore
) : LocalRepository {
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

    override suspend fun isLogin(): String = userDataStore.isLogin()

    override suspend fun isAccessToken(): String = userDataStore.isAccessToken()

    override fun createLoginSession(accessToken: String, id: String): Flow<Boolean> =
        userDataStore.createLoginSession(accessToken, id)

    override fun logout(): Flow<Boolean> =
        userDataStore.logout()
}