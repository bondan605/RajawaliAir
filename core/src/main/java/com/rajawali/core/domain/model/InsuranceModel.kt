package com.rajawali.core.domain.model

data class InsuranceModel(
    val travelInsurance: Int,
    val baggageInsurance: Int,
    val flightDelayInsurance: Int
)

object Insurance {
    val PriceList = InsuranceModel(
        travelInsurance = 100000,
        baggageInsurance = 13500,
        flightDelayInsurance = 60000
    )
}