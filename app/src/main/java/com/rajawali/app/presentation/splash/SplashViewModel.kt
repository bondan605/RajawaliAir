package com.rajawali.app.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.authentication.IsLoginUseCase

class SplashViewModel(loginUseCase: IsLoginUseCase) : ViewModel() {

    val isLogin: LiveData<CommonResult<String>> = loginUseCase.isLogin().asLiveData()
}