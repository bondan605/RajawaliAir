package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.MealModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetMealsUseCase(private val repository: RemoteRepository) {

    fun getMeals(): Flow<UCResult<List<MealModel>>> = flow {

        try {
            val response = repository.getFlightMeals()

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model = response.data.map { meal ->
                        DataMapper.MealsResponseToMealsDomain(meal)
                    }

                    emit(UCResult.Success(model))
                }

            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(UCResult.Error(e.message ?: Constant.FETCH_FAILED))
        }
    }
}