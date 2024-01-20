package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecentSearchUseCase(private val repository: LocalRepository) {

    fun getRecentSearch(): Flow<UCResult<List<SearchModel>>> = flow {
        val entity = repository.getRecentSearch()

        when (entity.isNotEmpty()) {
            true -> {
                val domain = entity.map {
                    DataMapper.recentSearchEntityToDomain(it)
                }

                emit(UCResult.Success(domain))
            }

            false -> emit(UCResult.Error(Constant.DATA_EMPTY))
        }
    }
}