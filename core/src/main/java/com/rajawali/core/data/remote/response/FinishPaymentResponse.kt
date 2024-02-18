package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class FinishPaymentResponse(

	@Json(name="data")
	val data: FinishPaymentData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class FinishPaymentData(

	@Json(name="receiverNumber")
	val receiverNumber: String? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="method")
	val method: String? = null,

	@Json(name="verifiedAt")
	val verifiedAt: String? = null,

//	@Json(name="reservation")
//	val reservation: Reservation,

	@Json(name="paidAt")
	val paidAt: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="paymentStatus")
	val paymentStatus: String? = null,

	@Json(name="updatedAt")
	val updatedAt: String? = null
)
