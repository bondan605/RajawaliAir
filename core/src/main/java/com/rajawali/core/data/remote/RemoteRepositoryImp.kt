package com.rajawali.core.data.remote

import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.FlightItem
import com.rajawali.core.data.remote.response.MealItem
import com.rajawali.core.data.remote.service.RajawaliAirService
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.util.Constant
import timber.log.Timber

class RemoteRepositoryImp(private val service: RajawaliAirService) : RemoteRepository {

    override suspend fun getAirports(): ApiResponse<List<ContentItem>> {
        val response = service.getAirports(Constant.THOUSAND)

        return when (response.success ?: false) {
            true -> {

                when (response.data?.empty) {
                    true ->
                        ApiResponse.Error(Constant.DATA_EMPTY)

                    false ->
                        ApiResponse.Success(response.data.content)

                    null ->
                        ApiResponse.Error(Constant.DATA_EMPTY)
                }
            }

            false -> {
                ApiResponse.Error(response.message ?: Constant.FETCH_FAILED)
            }
        }
    }

    override suspend fun getPreferredFlights(
        departure: String,
        destination: String,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureDate: String,
        seatType: String
    ): ApiResponse<List<FlightItem>> {
        return try {
            val response = service.getPreferredFlights(
                departure = departure,
                destination = destination,
                adultPassenger = adultPassenger,
                childPassenger = childPassenger,
                infantPassenger = infantPassenger,
                departureDate = departureDate,
                seatType = seatType,
            )

            Timber.d("getPreferredFlights -> Response: $response")

            when (response.success) {
                true -> {
                    when (response.data.empty) {
                        true ->
                            throw Exception(Constant.DATA_EMPTY)

                        false ->
                            ApiResponse.Success(response.data.content)

                        null -> {
                            throw Exception(Constant.DATA_EMPTY)
                        }
                    }
                }

                false ->
                    throw Exception(Constant.FETCH_FAILED)

                null ->
                    throw Exception(response.message ?: Constant.DATA_EMPTY)

            }

        } catch (e: Exception) {
            Timber.w(e)

            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }
    }

    override suspend fun getFlightMeals(): ApiResponse<List<MealItem>> {
        val response = service.getMeals()

        return try {

            when (response.success) {
                true -> {
                    when (response.data.empty) {
                        true ->
                            throw Exception(response.message)

                        false ->
                            ApiResponse.Success(response.data.content)

                        null ->
                            throw Exception(Constant.DATA_EMPTY)
                    }
                }

                false ->
                    throw Exception(response.message)

                null ->
                    throw Exception(Constant.DATA_EMPTY)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }
    }
}