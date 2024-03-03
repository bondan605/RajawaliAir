package com.rajawali.app.presentation.ui.auth.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.VerifyModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.authentication.VerifyUseCase

class VerifyViewModel(private val verifyUseCase: VerifyUseCase) : ViewModel() {

    fun verify(otp: String, email: String): LiveData<CommonResult<String>> {
        val model = VerifyModel(
            email = email,
            otp = otp,
        )

        return verifyUseCase.verify(model).asLiveData()
    }
}