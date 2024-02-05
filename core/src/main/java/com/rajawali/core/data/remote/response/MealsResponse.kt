package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class MealsResponse(

	@Json(name="data")
	val data: MealData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class MealItem(

	@Json(name="price")
	val price: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="thumbnailUrl")
	val thumbnailUrl: String? = null
)

@JsonClass(generateAdapter = true)
data class MealData(

	@Json(name="size")
	val size: Int? = null,

	@Json(name="totalPages")
	val totalPages: Int? = null,

	@Json(name="content")
	val content: List<MealItem>,

	@Json(name="empty")
	val empty: Boolean? = null
)