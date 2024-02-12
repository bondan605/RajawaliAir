package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.ReservationByIdModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetReservationByIdUseCase(private val remote: RemoteRepository) {

    fun getReservation(id: String): Flow<UCResult<ReservationByIdModel>> = flow{
        try {
            val response = remote.reservationById(id)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model =
                        DataMapper.reservationByIdResponseToReservationByIdModel(response.data)

                    emit(UCResult.Success(model))
                }
            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(UCResult.Error(e.message ?: Constant.FETCH_FAILED))
        }
    }

}