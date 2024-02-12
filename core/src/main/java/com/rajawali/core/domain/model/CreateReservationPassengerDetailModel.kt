package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateReservationPassengerDetailModel(

    @Json(name = "seatId")
    val seatId : String,

    @Json(name = "bagageAddOns")
    val baggage : String = "0",

    @Json(name = "mealsAddOns")
    val meals : List<String> = mutableListOf()
)
