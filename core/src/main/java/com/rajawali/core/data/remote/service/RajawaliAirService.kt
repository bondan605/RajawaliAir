package com.rajawali.core.data.remote.service

import com.rajawali.core.data.remote.response.AirportsResponse
import com.rajawali.core.data.remote.response.FlightResponse
import com.rajawali.core.util.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface RajawaliAirService {

    @GET("airports")
    suspend fun getAirports(
        @Query(Constant.PAGE_SIZE) size : Int
    ): AirportsResponse

    @GET("flights/depatures")
    suspend fun getPreferredFlights(
        @Query(Constant.SOURCE_AIRPORT) departure : String,
        @Query(Constant.DESTINATION_AIRPORT) destination : String,
        @Query(Constant.ADULT_PASSENGER) adultPassenger : Int,
        @Query(Constant.CHILD_PASSENGER) childPassenger : Int,
        @Query(Constant.INFANT_PASSENGER) infantPassenger : Int,
        @Query(Constant.DEPARTURE_DATE) departureDate : String,
        @Query(Constant.SEAT_TYPE) seatType : String,
    ): FlightResponse

}