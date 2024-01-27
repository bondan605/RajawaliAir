package com.rajawali.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightModel(
    val totalPrice: Int,

    val availableSeats: Int,

    val discount: Int,

    val destinationAirport: SearchModel,

    val arrivalDate: String,

    val points: Int,

    val sourceAirport: SearchModel,

    val airplane: AirplaneModel,

    val id: String,

    val departureDate: String,

    val seatPrice: Int,

    val classType: String,

    val departureTime: String,

    val arrivalTime: String,
) : Parcelable
