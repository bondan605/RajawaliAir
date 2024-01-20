package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.util.DataMapper

class AddRecentSearchUseCase(private val repository: LocalRepository) {

    fun addRecentSearch(search: SearchModel) : Boolean {
        val entity =  DataMapper.recentSearchDomainToEntity(search)

        return repository.addSearch(entity)
    }
}