package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PayReservationModel(

    @Json(name="reservationId")
    val reservationId : String,

    @Json(name="method")
    val paymentMethod : String,
)