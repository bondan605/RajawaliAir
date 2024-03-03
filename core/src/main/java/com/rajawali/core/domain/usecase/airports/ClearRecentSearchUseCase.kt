package com.rajawali.core.domain.usecase.airports

import com.rajawali.core.domain.repository.LocalRepository

class ClearRecentSearchUseCase(private val local : LocalRepository) {

    suspend fun clear() {
        local.clearRecentSearch()
    }
}