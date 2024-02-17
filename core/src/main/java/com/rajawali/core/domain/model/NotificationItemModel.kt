package com.rajawali.core.domain.model

import com.squareup.moshi.Json

data class NotificationItemModel(
    val createdAt: String,

    val description: String,

    val id: String,

    val notificationType: String,

    val updatedAt: String

)
