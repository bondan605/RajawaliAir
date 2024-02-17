package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class CreateLoginSessionUseCase(private val local: LocalRepository) {

    fun createSession(accessToken : String, id : String) : Flow<Boolean> =
        local.createLoginSession(accessToken, id)
}