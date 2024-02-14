package com.rajawali.core.domain.model

data class LoggedInUserModel(
    val id: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val accessToken: String,
    val refreshToken: String,
    val type: String,
)