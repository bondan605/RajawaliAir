package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class NotificationResponse(

	@Json(name="data")
	val data: NotificationData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class NotificationsItem(

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="notificationType")
	val notificationType: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class NotificationData(

	@Json(name="isSeen")
	val isSeen: Boolean? = null,

	@Json(name="notifications")
	val notifications: List<NotificationsItem>
)
