package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class AvailableSeatsResponse(

	@Json(name="data")
	val data: SeatsData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class SeatsItem(

	@Json(name="isAvailable")
	val isAvailable: Boolean? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="seatNo")
	val seatNo: String? = null
)

@JsonClass(generateAdapter = true)
data class SeatsData(

	@Json(name="seatPerCol")
	val seatPerCol: Int? = null,

	@Json(name="seats")
	val seats: List<SeatsItem>,

	@Json(name="classType")
	val classType: String? = null
)
