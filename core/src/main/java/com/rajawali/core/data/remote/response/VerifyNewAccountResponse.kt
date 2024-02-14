package com.rajawali.core.data.remote.response

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class VerifyNewAccountResponse(

//	@Json(name="data")
//	val data: VerifyData,

	@Json(name="success")
	val success: Boolean,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class VerifyData(

	@Json(name="phoneNumber")
	val phoneNumber: String? = null,
//
//	@Json(name="roles")
//	val roles: List<RolesItem?>? = null,

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

//@JsonClass(generateAdapter = true)
//data class RolesItem(
//
//	@Json(name="name")
//	val name: String? = null
//)
