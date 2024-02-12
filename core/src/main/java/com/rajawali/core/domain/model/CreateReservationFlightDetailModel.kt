package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateReservationFlightDetailModel(
    @Json(name="flightId")
    val flightId : String,

    @Json(name="useTravelAssurance")
    val useTravelInsurance : Boolean,

    @Json(name="useBagageAssurance")
    val useBaggageInsurance : Boolean,

    @Json(name="useFlightDelayAssurance")
    val useFlightDelayInsurance : Boolean,

    @Json(name="passengerDetailList")
    val passengerDetail : List<CreateReservationPassengerDetailModel>
)
