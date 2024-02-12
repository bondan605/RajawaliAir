package com.rajawali.core.domain.repository

import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.FlightItem
import com.rajawali.core.data.remote.response.MealItem
import com.rajawali.core.data.remote.response.PayReservationData
import com.rajawali.core.data.remote.response.ReservationByIdData
import com.rajawali.core.data.remote.response.ReservationData
import com.rajawali.core.data.remote.response.SeatsData
import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.PayReservationModel
import com.rajawali.core.domain.result.ApiResponse

interface RemoteRepository {

    suspend fun getAirports(): ApiResponse<List<ContentItem>>

    suspend fun getPreferredFlights(
        departure: String,
        destination: String,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureDate: String,
        seatType: String,
    ): ApiResponse<List<FlightItem>>

    suspend fun getFlightMeals(): ApiResponse<List<MealItem>>

    suspend fun getFlightAvailableSeats(id: String, seatType: String): ApiResponse<SeatsData>

    suspend fun createReservation(data: CreateReservationModel): ApiResponse<ReservationData>

    suspend fun payReservation(data: PayReservationModel): ApiResponse<PayReservationData>

    suspend fun reservationById(reservationId: String): ApiResponse<ReservationByIdData>
}