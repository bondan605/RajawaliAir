package com.rajawali.core.util

import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.data.remote.response.Airplane
import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.DestinationAirport
import com.rajawali.core.data.remote.response.FlightItem
import com.rajawali.core.data.remote.response.SourceAirport
import com.rajawali.core.domain.model.AirplaneModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.model.SearchModel

object DataMapper {

    fun recentSearchEntityToDomain(entity: RecentSearchEntity): SearchModel =
        SearchModel(
            id = entity.id,
            city = entity.city,
            cityCode = entity.cityCode,
            airport = entity.airport
        )

    fun recentSearchDomainToEntity(domain: SearchModel): RecentSearchEntity =
        RecentSearchEntity(
            id = domain.id,
            city = domain.city,
            cityCode = domain.cityCode,
            airport = domain.airport
        )

    fun airportsResponseToSearchDomain(response: ContentItem): SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )

    fun flightsResponseToDomain(
        response: FlightItem,
        departureTime : String,
        arrivalTime : String
    ): FlightModel =
        FlightModel(
            discount = response.discount ?: 0,

            destinationAirport = destinationAirportResponseToSearchDomain(response.destinationAirport),

            arrivalDate = response.arrivalDate ?: "",

            availableSeats = response.availableSeats ?: 0,

            points = response.points ?: 0,

            sourceAirport = sourceAirportResponseToSearchDomain(response.sourceAirport),

            airplane = airplaneResponseToAirplaneDomain(response.airplane),

            id = response.id ?: "",

            departureDate = response.departureDate ?: "",

            classType = response.classType ?: "",

            seatPrice = response.seatPrice ?: 0,

            totalPrice = response.totalPrice ?: 0,

            departureTime = departureTime,

            arrivalTime = arrivalTime,
        )

    private fun destinationAirportResponseToSearchDomain(response: DestinationAirport): SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )

    private fun sourceAirportResponseToSearchDomain(response: SourceAirport): SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )

    private fun airplaneResponseToAirplaneDomain(response: Airplane): AirplaneModel =
        AirplaneModel(
            id = response.id ?: "",
            airplaneCode = response.airplaneCode ?: ""
        )
}