package com.rajawali.core.domain.repository

import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getRecentSearch() : List<RecentSearchEntity>

    fun addSearch(search: RecentSearchEntity) : Boolean

}
