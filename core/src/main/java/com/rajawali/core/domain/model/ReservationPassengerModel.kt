package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservationPassengerModel (
    @Json(name="genderType")
    val gender : String,

    @Json(name="fullname")
    val fullName : String,

    @Json(name="ageType")
    val age : String
)