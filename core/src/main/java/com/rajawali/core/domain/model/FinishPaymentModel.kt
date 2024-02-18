package com.rajawali.core.domain.model

data class FinishPaymentModel(
    val receiverNumber: String,

    val createdAt: String,

    val method: String,

    val verifiedAt: String,

    val paidAt: String,

    val id: String,

    val paymentStatus: String,

    val updatedAt: String

)