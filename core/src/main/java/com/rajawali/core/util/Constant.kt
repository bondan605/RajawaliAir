package com.rajawali.core.util

import com.rajawali.core.domain.enums.PaymentStatusEnum

object Constant {

    //message
    const val DATA_NOT_FOUND = "No data is found"
    const val DATA_FOUND = "data is found"
    const val DATA_EMPTY = "data is empty"
    const val FETCH_FAILED = "Unable to fetch data"

    //api
    const val BASE_URL = "https://rajawali-production.up.railway.app/api/"
    const val API_VERSION = "v1/"
    const val PAGE_SIZE = "pageSize"
    const val SOURCE_AIRPORT = "sourceAirportId"
    const val DESTINATION_AIRPORT = "destAirportId"
    const val ADULT_PASSENGER = "adultsNumber"
    const val CHILD_PASSENGER = "childsNumber"
    const val INFANT_PASSENGER = "infantsNumber"
    const val DEPARTURE_DATE = "departureDate"
    const val SEAT_TYPE = "classType"


    const val THOUSAND = 1000

    //payment status
    val statusToEnumMap = mapOf(
        "waiting" to PaymentStatusEnum.PAYMENT_WAITING,
        "pending" to PaymentStatusEnum.PAYMENT_PENDING,
        "successful" to PaymentStatusEnum.PAYMENT_SUCCESS,
        "canceled" to PaymentStatusEnum.PAYMENT_CANCELED,
        "valid" to PaymentStatusEnum.PAYMENT_NOT_VALID,
        "null" to PaymentStatusEnum.PAYMENT_NULL
    )

}