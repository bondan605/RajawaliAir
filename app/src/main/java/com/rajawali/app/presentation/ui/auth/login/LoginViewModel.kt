package com.rajawali.app.presentation.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.LoggedInUserModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    fun login(id: String, password: String): LiveData<CommonResult<LoggedInUserModel>> =
        loginUseCase.login(id, password).asLiveData()

}