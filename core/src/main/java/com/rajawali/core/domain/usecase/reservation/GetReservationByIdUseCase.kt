package com.rajawali.core.domain.usecase.reservation

import com.rajawali.core.domain.model.ReservationByIdModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import timber.log.Timber

class GetReservationByIdUseCase(private val remote: RemoteRepository) {

//    fun getReservation(id: String): Flow<CommonResult<ReservationByIdModel>> = flow{
//        try {
//            val response = remote.reservationById(id)
//
//            when (response) {
//                is ApiResponse.Error ->
//                    throw Exception(response.errorMessage)
//
//                is ApiResponse.Success -> {
//                    val model =
//                        DataMapper.reservationByIdResponseToReservationByIdModel(response.data)
//
//                    emit(CommonResult.Success(model))
//                }
//            }
//        } catch (e: Exception) {
//            Timber.w(e)
//            emit(CommonResult.Error(e.message ?: Constant.FETCH_FAILED))
//        }
//    }

    suspend fun getReservation(id: String): CommonResult<ReservationByIdModel> =
        try {
            val response = remote.reservationById(id)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model =
                        DataMapper.reservationByIdResponseToReservationByIdModel(response.data)

                    CommonResult.Success(model)
                }
            }
        } catch (e: Exception) {
            Timber.w(e)
            CommonResult.Error(e.message ?: Constant.FETCH_FAILED)
        }

}