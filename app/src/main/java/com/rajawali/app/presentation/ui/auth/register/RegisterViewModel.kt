package com.rajawali.app.presentation.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.RegisterModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.authentication.RegisterUseCase

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    fun register(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
    ): LiveData<CommonResult<String>> {
        val model = RegisterModel(
            fullName = fullName,
            email = email,
            phone = phone,
            password = password,
            confirmPassword = confirmPassword,
        )

        return registerUseCase.register(model).asLiveData()
    }
}