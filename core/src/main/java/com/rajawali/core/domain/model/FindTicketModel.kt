package com.rajawali.core.domain.model

import android.os.Parcelable
import com.rajawali.core.domain.enums.PassengerClassEnum
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class FindTicketModel(
    val departureId: String,
    val departureDate: LocalDate,
    val destinationId: String,
    val seatType: PassengerClassEnum,
    val adultPassenger: Int,
    val childPassenger: Int,
    val infantPassenger: Int,
    val departureCity: String,
    val destinationCity: String
) : Parcelable
