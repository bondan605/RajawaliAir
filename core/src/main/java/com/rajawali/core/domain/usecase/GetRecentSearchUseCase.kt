package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetRecentSearchUseCase(private val repository: LocalRepository) {

    fun getRecentSearch(): Flow<CommonResult<List<SearchModel>>> = flow {
        try {
            val entity = repository.getRecentSearch()

            when (entity.isNotEmpty()) {
                true -> {
                    val domain = entity.map {
                        DataMapper.recentSearchEntityToDomain(it)
                    }
                    if (domain.isEmpty())
                        throw Exception(entity.toString())

                    emit(CommonResult.Success(domain))
                }

                false ->
                    throw Exception(Constant.FETCH_FAILED)

            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(e.message ?: Constant.FETCH_FAILED))
        }

    }
}