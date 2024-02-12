package com.rajawali.core.domain.model

import com.squareup.moshi.Json

// this model is children of Payment model
data class BookingModel(
    val expiredAt: String,
    val phoneNumber: String,
    val totalPrice: Int,
    val id: String,
    val fullName: String,
    val classType: String,
    val email: String,
    val genderType: String
)
