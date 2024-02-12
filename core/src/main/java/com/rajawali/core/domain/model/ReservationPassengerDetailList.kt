package com.rajawali.core.domain.model

data class ReservationPassengerDetailListModel(
    val seatId : String,
    val baggageAddOns : Int,
    val mealsAddOns : List<ReservationMealsAddOnsModel>
)
