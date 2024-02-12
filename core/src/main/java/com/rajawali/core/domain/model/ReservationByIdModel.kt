package com.rajawali.core.domain.model

data class ReservationByIdModel(
    val id: String,
    val paymentStatus: String,
    val expiredAt: String,
)