package com.rajawali.core.domain.model

data class ReservationModel(
    val id : String,
    val userId : String? = null,
    val promoCode : String? = null,
    val classType : String,
    val gender : String,
    val fullName : String,
    val email : String,
    val phoneNumber : String,
    val paymentStatus : String,
    val passengers : List<ReservationPassengerModel>,
    val flightDetailList : List<ReservationFlightDetailListModel>,
    val expiredAt : String,
)