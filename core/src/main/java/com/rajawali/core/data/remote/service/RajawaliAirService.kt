package com.rajawali.core.data.remote.service

import com.rajawali.core.data.remote.response.AirportsResponse
import retrofit2.http.GET

interface RajawaliAirService {

    @GET("airports")
    suspend fun getAirports() : AirportsResponse
}