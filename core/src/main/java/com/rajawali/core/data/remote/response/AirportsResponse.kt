package com.rajawali.core.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AirportsResponse(

	@Json(name="data")
	val data: Data? = null,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class ContentItem(

	@Json(name="country")
	val country: String? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="city")
	val city: String? = null,

	@Json(name="cityCode")
	val cityCode: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class Data(

	@Json(name="size")
	val size: Int? = null,

	@Json(name="content")
	val content: List<ContentItem>,

	@Json(name="empty")
	val empty: Boolean? = null
)
