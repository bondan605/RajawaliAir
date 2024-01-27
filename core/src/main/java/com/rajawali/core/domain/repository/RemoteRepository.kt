package com.rajawali.core.domain.repository

import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.FlightItem
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
}