package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.repository.RemoteRepository

class GetFlightsUseCase(private val repository: RemoteRepository) {

    //TODO Flight Duration is not yet implemented.
//    private suspend fun getFlights(
//        seatType: PassengerClassEnum,
//    ): UCResult<List<FlightModel>> {
//        val response = repository.getFlights()
//
//
//        return when (response) {
//            is ApiResponse.Error ->
//                UCResult.Error(response.errorMessage)
//
//            is ApiResponse.Success -> {
//                val model = response.data.map { flightData ->
//
//                    val departureTime = getTime(flightData.departureDate)
//                    val arrivalTime = getTime(flightData.arrivalDate)
//
//                    val selectedAvailableSeats = getPreferredDataBySeatType(
//                        seatType,
//                        flightData.economyAvailableSeats,
//                        flightData.businessAvailableSeats,
//                        flightData.firstAvailableSeats
//                    )
//
//                    val selectedSeatsPrice = getPreferredDataBySeatType(
//                        seatType,
//                        flightData.economySeatsPrice,
//                        flightData.businessSeatsPrice,
//                        flightData.firstSeatsPrice
//                    )
//
//                    DataMapper.flightsResponseToDomain(
//                        flightData,
//                        selectedSeatsPrice,
//                        selectedAvailableSeats,
//                        departureTime,
//                        arrivalTime
//                    )
//                }
//
//
//
//                UCResult.Success(model)
//            }
//        }
//    }
//
//    private fun getTime(nullableDate: String?): String {
//        val date = nullableDate ?: ""
//
//        return if (date.length >= 16)
//            date.slice(12..16)
//        else
//            ""
//    }
//
//    private fun getPreferredDataBySeatType(
//        seatType: PassengerClassEnum,
//        economy: Int?,
//        business: Int?,
//        first: Int?,
//    ): Int {
//        val economyPrice = economy ?: 0
//        val businessPrice = business ?: 0
//        val firstPrice = first ?: 0
//
//        return when (seatType) {
//            PassengerClassEnum.ECONOMY ->
//                economyPrice
//
//            PassengerClassEnum.BUSINESS ->
//                businessPrice
//
//            PassengerClassEnum.FIRST ->
//                firstPrice
//
//            PassengerClassEnum.NULL ->
//                0
//        }
//    }
//
//    fun getPreferredFlight(
//        preferredDeparture: String,
//        preferredDestination: String,
//        preferredDate: String,
//        preferredSeatType: PassengerClassEnum,
//        preferredPassenger: Int
//    ): Flow<UCResult<List<FlightModel>>> = flow {
//        val flight = getFlights(preferredSeatType)
//
//        when (flight) {
//            is UCResult.Error ->
//                emit(UCResult.Error(flight.errorMessage))
//
//            is UCResult.Success -> {
//                val availableDeparture = flight.data.filter { data ->
//                    data.sourceAirport.cityCode.contains(preferredDeparture, ignoreCase = true)
//                }
//
//                val availableDestination =
//                    if (availableDeparture.isNotEmpty())
//                        availableDeparture.filter { data ->
//                            data.destinationAirport.cityCode.contains(
//                                preferredDestination,
//                                ignoreCase = true
//                            )
//                        }
//                    else
//                        availableDeparture
//
//
//                val availableDate =
//                    if (availableDestination.isNotEmpty())
//                        availableDestination.filter { data ->
//                            val availableDepartureDate = data.departureDate.take(10)
//
//                            availableDepartureDate.contains(preferredDate, ignoreCase = true)
//                        }
//                    else
//                        availableDestination
//
//
//                val isSeatAvailable =
//                    if (availableDate.isNotEmpty())
//                        availableDate.filter { data ->
//                            data.availableSeats >= preferredPassenger
//                        }
//                    else
//                        availableDate
//
//                Timber.d("getPreferredFlight : $isSeatAvailable")
//
//                emit(UCResult.Success(isSeatAvailable))
//            }
//        }
//    }
    //TODO

}