package com.rajawali.core.domain.model

import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.PassengerCategoryEnum

data class PassengerModel(
    val id: Int,
    val genderType: String,
    val fullName: String,
    val ageType: PassengerCategoryEnum,
    val checkBoxId: CourtesyEnum,
)

//"genderType": "WOMAN",
//"fullname": "Shania Gracia",
//"ageType": "CHILD"


data class PassengerDetailModel(
    val seatId: String,
    val baggageAddOns: String,
    val mealsAddOns: List<String>,
)

//"seatId": "bb3ba358-a12c-4711-9a81-7db3ee0cb3c2",
//"bagageAddOns": 5,
//"mealsAddOns": [
//"72cb3bb9-fb50-4855-b07e-2ff3d50fd8be",
//"5b523bdb-24c8-4a5e-99cd-c946f78ee9b2"
//]