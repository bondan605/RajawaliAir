package com.rajawali.core.domain.usecase.payment

import com.rajawali.core.domain.model.PayReservationModel
import com.rajawali.core.domain.model.PaymentRecordModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class PayReservationUseCase(private val remote: RemoteRepository) {

    fun payReservation(data: PayReservationModel) : Flow<CommonResult<PaymentRecordModel>> = flow{
        try {
            val response = remote.payReservation(data)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model = DataMapper.payReservationDataResponseToPaymentModel(response.data)

                    emit(CommonResult.Success(model))
                }
            }

        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(e.message ?: Constant.FETCH_FAILED))
        }

    }
}