package com.rajawali.core.domain.model

import com.rajawali.core.domain.enums.PassengerClassEnum

data class BuyerContactModel(
    val classType: PassengerClassEnum,
    val genderType: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val promoCode: String = "RQ_GNTGBGT",
    )

//"classType": "ECONOMY",
//"genderType": "MAN",
//"fullname": "Erki Kadhafi",
//"email": "erki@gmail.com",
//"phoneNumber": "081259781977",
//"promoCode": "RQ_GNTGBGT",

