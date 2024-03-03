package com.rajawali.core.domain.usecase.authentication

import com.rajawali.core.domain.repository.LocalRepository
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class IsLoginUseCase(private val local: LocalRepository) {

    fun isLogin(): Flow<CommonResult<String>> = flow {
        try {
            val data = local.isLogin()

            if (data.isEmpty())
                throw Exception(Constant.DATA_EMPTY)
            else
                emit(CommonResult.Success(data))
        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(e.message ?: Constant.DATA_EMPTY))
        }
    }
}