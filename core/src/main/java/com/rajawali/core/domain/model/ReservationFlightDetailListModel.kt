package com.rajawali.core.domain.model

data class ReservationFlightDetailListModel(
    val id : String,
    val flightId : String,
    val useTravelAssurance : Boolean,
    val useBaggageAssurance : Boolean,
    val useFlightDelayAssurance : Boolean,
    val seatPrice : Int,
    val totalPrice : Int,
    val passengerDetailList : List<ReservationPassengerDetailListModel>,
)