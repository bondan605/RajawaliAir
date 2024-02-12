package com.rajawali.core.domain.model


//this is for create payment response. I don't actually know what to name it.
data class PaymentRecordModel(
    val receiverNumber: String,
    val createdAt: String,
    val method: String,
    val verifiedAt: String,
    val reservation: BookingModel,
    val paidAt: String,
    val id: String,
    val paymentStatus: String,
    val updatedAt: String,
)
