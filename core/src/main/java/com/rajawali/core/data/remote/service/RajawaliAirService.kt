package com.rajawali.core.data.remote.service

import com.rajawali.core.data.remote.response.AirportsResponse
import com.rajawali.core.data.remote.response.AvailableSeatsResponse
import com.rajawali.core.data.remote.response.CreateReservationResponse
import com.rajawali.core.data.remote.response.FlightResponse
import com.rajawali.core.data.remote.response.LoginResponse
import com.rajawali.core.data.remote.response.MealsResponse
import com.rajawali.core.data.remote.response.NotificationResponse
import com.rajawali.core.data.remote.response.PayReservationResponse
import com.rajawali.core.domain.model.RegisterModel
import com.rajawali.core.data.remote.response.RegisterResponse
import com.rajawali.core.data.remote.response.ReservationByIdResponse
import com.rajawali.core.data.remote.response.VerifyNewAccountResponse
import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.LoginModel
import com.rajawali.core.domain.model.PayReservationModel
import com.rajawali.core.domain.model.VerifyModel
import com.rajawali.core.util.Constant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RajawaliAirService {

    @GET("airports")
    suspend fun getAirports(
        @Query(Constant.PAGE_SIZE) size : Int
    ): AirportsResponse

    @GET("flights/departures")
    suspend fun getPreferredFlights(
        @Query(Constant.SOURCE_AIRPORT) departure : String,
        @Query(Constant.DESTINATION_AIRPORT) destination : String,
        @Query(Constant.ADULT_PASSENGER) adultPassenger : Int,
        @Query(Constant.CHILD_PASSENGER) childPassenger : Int,
        @Query(Constant.INFANT_PASSENGER) infantPassenger : Int,
        @Query(Constant.DEPARTURE_DATE) departureDate : String,
        @Query(Constant.SEAT_TYPE) seatType : String,
    ): FlightResponse

    @GET("meals")
    suspend fun getMeals(
        @Query(Constant.PAGE_SIZE) size : Int = Constant.THOUSAND
    ) : MealsResponse

    @GET("reservations/available-seats/{id}/{seatType}")
    suspend fun getFlightAvailableSeats(
        @Path("id") flightId : String,
        @Path("seatType") seatType : String
    ) : AvailableSeatsResponse

    @GET("reservations/{id}")
    suspend fun reservationById(
        @Path("id") reservationId : String,
        ) : ReservationByIdResponse


    @GET("notifications/user/{id}")
    suspend fun getNotification(
        @Path("id") id : String,
//        @HeaderMap headers: Map<String, String>,
        @Header("Authorization") header: String,
    ) : NotificationResponse

    @POST("reservations")
    suspend fun createReservation(
        @Body data : CreateReservationModel
    ) : CreateReservationResponse

    @POST("payments/create")
    suspend fun payReservation(
        @Body data : PayReservationModel
    ) : PayReservationResponse

    @POST("auth/signin")
    suspend fun login(
        @Body data : LoginModel
    ) : LoginResponse

    @POST("auth/sign-up")
    suspend fun register(
        @Body data : RegisterModel
    ) : RegisterResponse

    @POST("auth/enable-user")
    suspend fun verifyNewAccount(
        @Body data : VerifyModel
    ) : VerifyNewAccountResponse


}