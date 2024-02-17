package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.NotificationModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetNotificationUseCase(private val remote: RemoteRepository) {

    fun getNotification(id: String, accessToken: String): Flow<CommonResult<NotificationModel>> =
        flow {
            try {
                val response = remote.getNotification(id, accessToken)

                when (response) {
                    is ApiResponse.Error ->
                        throw Exception(response.errorMessage)

                    is ApiResponse.Success -> {
                        val model =
                            DataMapper.notificationDataResponseToNotificationModel(response.data)

                        emit(CommonResult.Success(model))
                    }
                }

            } catch (e: Exception) {
                Timber.w(e)
                emit(CommonResult.Error(e.message ?: Constant.FETCH_FAILED))
            }
        }
}