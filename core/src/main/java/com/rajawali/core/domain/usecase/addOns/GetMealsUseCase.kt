package com.rajawali.core.domain.usecase.addOns

import com.rajawali.core.domain.model.MealModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetMealsUseCase(private val repository: RemoteRepository) {

    fun getMeals(): Flow<CommonResult<List<MealModel>>> = flow {

        try {
            val response = repository.getFlightMeals()

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model = response.data.map { meal ->
                        DataMapper.mealsResponseToMealsDomain(meal)
                    }

                    emit(CommonResult.Success(model))
                }

            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(e.message ?: Constant.FETCH_FAILED))
        }
    }
}