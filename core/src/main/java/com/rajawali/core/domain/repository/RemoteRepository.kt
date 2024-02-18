package com.rajawali.core.domain.repository

import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.FinishPaymentData
import com.rajawali.core.data.remote.response.FlightItem
import com.rajawali.core.data.remote.response.LoginData
import com.rajawali.core.data.remote.response.MealItem
import com.rajawali.core.data.remote.response.NotificationData
import com.rajawali.core.data.remote.response.PayReservationData
import com.rajawali.core.data.remote.response.RegisterData
import com.rajawali.core.data.remote.response.ReservationByIdData
import com.rajawali.core.data.remote.response.ReservationData
import com.rajawali.core.data.remote.response.SeatsData
import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.LoginModel
import com.rajawali.core.domain.model.PayReservationModel
import com.rajawali.core.domain.model.RegisterModel
import com.rajawali.core.domain.model.VerifyModel
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

    suspend fun login(data: LoginModel): ApiResponse<LoginData>

    suspend fun register(data: RegisterModel): ApiResponse<RegisterData>

    suspend fun verifyNewAccount(data: VerifyModel): ApiResponse<String>

    suspend fun getNotification(
        id: String, accessToken: String
    ): ApiResponse<NotificationData>

    suspend fun finishPayment(id: String): ApiResponse<FinishPaymentData>

}