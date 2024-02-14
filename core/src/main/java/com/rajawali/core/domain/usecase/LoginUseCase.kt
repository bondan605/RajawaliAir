package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.LoggedInUserModel
import com.rajawali.core.domain.model.LoginModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class LoginUseCase(private val remote: RemoteRepository) {

    fun login(email: String, password: String): Flow<CommonResult<LoggedInUserModel>> = flow {
        try {
            val data = LoginModel(email, password)
            val response = remote.login(data)

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {
                    val model = DataMapper.loginResponseToLoggedInUserModel(response.data)

                    emit(CommonResult.Success(model))
                }
            }
        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(Constant.ACCOUNT_NOT_FOUND))
        }
    }
}
