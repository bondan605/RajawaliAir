package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class NotificationModel(
    val isSeen: Boolean,

    val notifications: List<NotificationItemModel>

)
