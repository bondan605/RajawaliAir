package com.rajawali.core.domain.usecase.airports

import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.util.DataMapper
import com.rajawali.core.util.DateFormat

class AddRecentSearchUseCase(private val repository: LocalRepository) {

    suspend fun addRecentSearch(search: SearchModel) {
        val todayDateTime = DateFormat.getTodayLocalDateTimeInLong()
        val entity = DataMapper.recentSearchDomainToEntity(search, todayDateTime)
        repository.addSearch(entity)

    }

}