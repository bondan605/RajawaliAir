package com.rajawali.app.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.IsLoginModel
import com.rajawali.core.domain.model.NotificationModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.GetLoggedInDataUseCase
import com.rajawali.core.domain.usecase.GetNotificationUseCase

class NotificationViewModel(
    loggedInData: GetLoggedInDataUseCase,
    private val notificationUseCase: GetNotificationUseCase
) : ViewModel() {

    val getLoggedInData: LiveData<CommonResult<IsLoginModel>> =
        loggedInData.getLoggedInData().asLiveData()

    fun getNotification(
        id: String,
        accessToken: String,
    ): LiveData<CommonResult<NotificationModel>> =
        notificationUseCase.getNotification(id, accessToken).asLiveData()
}