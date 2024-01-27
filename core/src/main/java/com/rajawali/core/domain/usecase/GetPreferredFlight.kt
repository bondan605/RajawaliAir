package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetPreferredFlight(private val repo: RemoteRepository) {
    fun getPreferredFlight(
        departure: String,
        destination: String,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureDate: String,
        seatType: String,
    ): Flow<UCResult<List<FlightModel>>> = flow {
        try {
            val response = repo.getPreferredFlights(
                departure = departure,
                destination = destination,
                adultPassenger = adultPassenger,
                childPassenger = childPassenger,
                infantPassenger = infantPassenger,
                departureDate = departureDate,
                seatType = seatType,
            )

            Timber.d("getPreferredFlight -> Response: $response")

            when (response) {
                is ApiResponse.Error ->
                    throw Exception(response.errorMessage)

                is ApiResponse.Success -> {

                    val model = response.data.map {
                        val departureTime = getTime(it.departureDate)
                        val arrivalTime = getTime(it.arrivalDate)

                        DataMapper.flightsResponseToDomain(
                            response = it,
                            departureTime = departureTime,
                            arrivalTime = arrivalTime
                        )
                    }
                    Timber.d("getPreferredFlight $model")

                    emit(UCResult.Success(model))
                }
            }


        } catch (e: Exception) {
            Timber.w(e)

            emit(UCResult.Error(e.message ?: Constant.FETCH_FAILED))
        }
    }

    private fun getTime(nullableDate: String?): String {
        val date = nullableDate ?: ""

        return if (date.length >= 15)
            date.slice(11..15)
        else
            ""
    }

}