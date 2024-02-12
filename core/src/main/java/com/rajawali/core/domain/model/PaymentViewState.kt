package com.rajawali.core.domain.model

data class PaymentViewState(
    val reservationId: String = "",
    val paymentMethod: String = "",
    val paymentInProgress: Boolean = false
)
