package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class LoginResponse(

	@Json(name="data")
	val data: LoginData,

	@Json(name="success")
	val success: Boolean? = null,

	@Json(name="message")
	val message: String? = null,

	@Json(name="status")
	val status: String? = null
)

@JsonClass(generateAdapter = true)
data class LoginData(

	@Json(name="phoneNumber")
	val phoneNumber: String? = null,

//	@Json(name="roles")
//	val roles: List<UserRolesItem>,

	@Json(name="fullName")
	val fullName: String? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="accessToken")
	val accessToken: String? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="refreshToken")
	val refreshToken: String? = null
)

@JsonClass(generateAdapter = true)
data class UserRolesItem(

	@Json(name="name")
	val name: String? = null
)
