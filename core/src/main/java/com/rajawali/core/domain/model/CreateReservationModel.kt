package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateReservationModel (

    @Json(name="classType")
    val classType : String,

    @Json(name="genderType")
    val gender : String,

    @Json(name="fullname")
    val fullName : String,

    @Json(name="email")
    val email : String,

    @Json(name="phoneNumber")
    val phoneNumber : String,

    @Json(name="promoCode")
    val promoCode : String? = null,

    @Json(name="userId")
    val userId : String? = null,

    @Json(name="passengerList")
    val passengerList : List<ReservationPassengerModel>,

    @Json(name="flightDetailList")
    val flightDetailList : List<CreateReservationFlightDetailModel>
)
