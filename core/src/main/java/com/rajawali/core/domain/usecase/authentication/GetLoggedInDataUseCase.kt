package com.rajawali.core.domain.usecase.authentication

import com.rajawali.core.domain.model.IsLoginModel
import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetLoggedInDataUseCase(private val localRepository: LocalRepository) {

    fun getLoggedInData(): Flow<CommonResult<IsLoginModel>> = flow {
        try {
            val accessToken = localRepository.isAccessToken()
            val id = localRepository.isLogin()

            if (accessToken.isEmpty())
                throw IllegalAccessException(Constant.DATA_EMPTY)

            if (id.isEmpty())
                throw IllegalAccessException(Constant.DATA_EMPTY)

            if (accessToken.isNotEmpty() && id.isNotEmpty()) {
                val model = IsLoginModel(
                    accessToken = accessToken,
                    id = id,
                )

                emit(CommonResult.Success(model))
            }
        } catch (e: IllegalAccessException) {
            Timber.w(e)
            emit(CommonResult.Error(e.message ?: Constant.DATA_EMPTY))
        }
    }
}