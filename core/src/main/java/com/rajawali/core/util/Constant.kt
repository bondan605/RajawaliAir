package com.rajawali.core.util

import com.rajawali.core.domain.enums.BaggageEnum
import com.rajawali.core.domain.enums.PaymentStatusEnum

object Constant {

    //login
    const val LOGIN_FAILED = "Login failed"

    //data Store preference
    const val USER_PREF = "userRajawali"

    //message
    const val DATA_NOT_FOUND = "No data is found"
    const val DATA_FOUND = "data is found"
    const val DATA_EMPTY = "data is empty"
    const val FETCH_FAILED = "Unable to fetch data"
    const val ACCOUNT_NOT_FOUND = "Account is not found"
    const val OTP_VERIFY_FAILED = "Wrong code"

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
        "Waiting for Payment" to PaymentStatusEnum.PAYMENT_WAITING,
        "Purchase Pending" to PaymentStatusEnum.PAYMENT_PENDING,
        "Purchase Successful" to PaymentStatusEnum.PAYMENT_SUCCESS,
        "Purchase Canceled" to PaymentStatusEnum.PAYMENT_CANCELED,
        "Payment Not Valid" to PaymentStatusEnum.PAYMENT_NOT_VALID,
        "null" to PaymentStatusEnum.PAYMENT_NULL
    )
    const val PURCHASE_SUCCESS = "Purchase Successful"

    //payment network fail
    const val PAYMENT_NO_NETWORK = "Network is disturb. Please check your network"

    //baggage
    val baggageEnumToStringMap = mapOf(
        BaggageEnum.KG0 to "0",
        BaggageEnum.KG5 to "5",
        BaggageEnum.KG10 to "10",
        BaggageEnum.KG20 to "20",
        BaggageEnum.KG30 to "30",
        BaggageEnum.KG40 to "40",
    )

}