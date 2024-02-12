package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class PayReservationResponse(

	@Json(name="data")
	val data: PayReservationData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class PayReservationData(

	@Json(name="receiverNumber")
	val receiverNumber: String? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="method")
	val method: String? = null,

	@Json(name="verifiedAt")
	val verifiedAt: String? = null,

	@Json(name="reservation")
	val reservation: Reservation,

	@Json(name="paidAt")
	val paidAt: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="paymentStatus")
	val paymentStatus: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class Reservation(

	@Json(name="expiredAt")
	val expiredAt: String? = null,

	@Json(name="phoneNumber")
	val phoneNumber: String? = null,

	@Json(name="totalPrice")
	val totalPrice: Int? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="fullname")
	val fullname: String? = null,

	@Json(name="classType")
	val classType: String? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="genderType")
	val genderType: String? = null
)
