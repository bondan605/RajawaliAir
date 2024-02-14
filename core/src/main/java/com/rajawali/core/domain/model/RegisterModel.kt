package com.rajawali.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterModel(

    @Json(name = "fullName")
    val fullName: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "phoneNumber")
    val phone: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "confirmationPassword")
    val confirmPassword: String,

    )
