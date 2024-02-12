package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.ReservationModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CreateReservationUseCase(private val remote: RemoteRepository) {

    fun createReservation(data: CreateReservationModel) : Flow<UCResult<ReservationModel>> = flow {
        try {

            val response = remote.createReservation(data)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model = DataMapper.reservationResponseToReservationModel(response.data)

                    emit(UCResult.Success(model))
                }
            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(UCResult.Error(e.message ?: Constant.FETCH_FAILED))
        }
    }
}