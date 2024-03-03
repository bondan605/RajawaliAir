package com.rajawali.core.domain.usecase.authentication

import com.rajawali.core.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val local: LocalRepository) {

    fun logout() : Flow<Boolean> =
        local.logout()
}