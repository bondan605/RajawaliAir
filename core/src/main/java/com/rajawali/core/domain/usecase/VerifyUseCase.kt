package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.VerifyModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class VerifyUseCase(private val remote: RemoteRepository) {

    fun verify(data: VerifyModel): Flow<CommonResult<String>> = flow {
        try {
            val response = remote.verifyNewAccount(data)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success ->
                    emit(CommonResult.Success(response.data))
            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(e.message ?: Constant.OTP_VERIFY_FAILED))
        }
    }
}