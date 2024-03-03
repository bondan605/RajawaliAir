package com.rajawali.core.domain.usecase.authentication

import com.rajawali.core.domain.model.RegisterModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class RegisterUseCase(private val remote: RemoteRepository) {

    fun register(data: RegisterModel): Flow<CommonResult<String>> = flow {
        try {
            val response = remote.register(data)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    if (response.data.message != null)
                        emit(CommonResult.Success(response.data.message))
                    else
                        throw Exception(Constant.DATA_EMPTY)

                }
            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(Constant.DATA_EMPTY))
        }

    }

}