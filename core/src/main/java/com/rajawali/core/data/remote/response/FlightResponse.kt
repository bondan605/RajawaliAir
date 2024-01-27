package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class FlightResponse(

	@Json(name="data")
	val data: FlightData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class Airplane(

	@Json(name="id")
	val id: String? = null,

	@Json(name="airplaneCode")
	val airplaneCode: String? = null
)

@JsonClass(generateAdapter = true)
data class SourceAirport(

	@Json(name="country")
	val country: String? = null,

	@Json(name="city")
	val city: String? = null,

	@Json(name="cityCode")
	val cityCode: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null
)

@JsonClass(generateAdapter = true)
data class DestinationAirport(

	@Json(name="country")
	val country: String? = null,

	@Json(name="city")
	val city: String? = null,

	@Json(name="cityCode")
	val cityCode: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null
)

@JsonClass(generateAdapter = true)
data class FlightData(

	@Json(name="content")
	val content: List<FlightItem>,

	@Json(name="empty")
	val empty: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class FlightItem(

	@Json(name="totalPrice")
	val totalPrice: Int? = null,

	@Json(name="availableSeats")
	val availableSeats: Int? = null,

	@Json(name="discount")
	val discount: Int? = null,

	@Json(name="destinationAirport")
	val destinationAirport: DestinationAirport,

	@Json(name="arrivalDate")
	val arrivalDate: String? = null,

	@Json(name="points")
	val points: Int? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="sourceAirport")
	val sourceAirport: SourceAirport,

	@Json(name="airplane")
	val airplane: Airplane,

	@Json(name="id")
	val id: String? = null,

	@Json(name="departureDate")
	val departureDate: String? = null,

	@Json(name="seatPrice")
	val seatPrice: Int? = null,

	@Json(name="classType")
	val classType: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
)
