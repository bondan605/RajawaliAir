package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyModel(

    @Json(name = "email")
    val email: String,

    @Json(name = "otpCode")
    val otp: String,
)