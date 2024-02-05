package com.rajawali.core.domain.model

import com.rajawali.core.domain.enums.BaggageEnum

data class PassengerBaggageModel(
    val id : Int,
    val baggage : BaggageEnum = BaggageEnum.KG0
)