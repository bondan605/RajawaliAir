package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class CreateReservationResponse(

	@Json(name="data")
	val data: ReservationData? = null,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class MealsAddOnsItem(

	@Json(name="price")
	val price: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null
)

@JsonClass(generateAdapter = true)
data class PassengersItem(

	@Json(name="fullname")
	val fullname: String? = null,

	@Json(name="genderType")
	val genderType: String? = null,

	@Json(name="ageType")
	val ageType: String? = null
)

@JsonClass(generateAdapter = true)
data class FlightDetailListItem(

	@Json(name="totalPrice")
	val totalPrice: Int? = null,

	@Json(name="flightId")
	val flightId: String? = null,

	@Json(name="useBagageAssurance")
	val useBagageAssurance: Boolean? = null,

	@Json(name="passengerDetailList")
	val passengerDetailList: List<PassengerDetailListItem>,

	@Json(name="id")
	val id: String? = null,

	@Json(name="useTravelAssurance")
	val useTravelAssurance: Boolean? = null,

	@Json(name="seatPrice")
	val seatPrice: Int? = null,

	@Json(name="useFlightDelayAssurance")
	val useFlightDelayAssurance: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class PassengerDetailListItem(

	@Json(name="bagageAddOns")
	val bagageAddOns: Int? = null,

	@Json(name="seatId")
	val seatId: String? = null,

	@Json(name="mealsAddOns")
	val mealsAddOns: List<MealsAddOnsItem>
)

@JsonClass(generateAdapter = true)
data class ReservationData(

	@Json(name="passengers")
	val passengers: List<PassengersItem?>,

	@Json(name="totalPrice")
	val totalPrice: Int? = null,

	@Json(name="flightDetailList")
	val flightDetailList: List<FlightDetailListItem>,

	@Json(name="promo")
	val promo: String? = null,

	@Json(name="expiredAt")
	val expiredAt: String? = null,

	@Json(name="phoneNumber")
	val phoneNumber: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="fullname")
	val fullname: String? = null,

	@Json(name="user")
	val user: String? = null,

	@Json(name="classType")
	val classType: String? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="paymentStatus")
	val paymentStatus: String? = null,

	@Json(name="genderType")
	val genderType: String? = null
)
