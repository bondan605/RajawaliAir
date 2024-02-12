package com.rajawali.core.domain.model

import com.rajawali.core.data.remote.response.SeatsItem

data class AvailableSeatsModel(
    val seatPerCol: Int,

    val seats: List<SeatsModel>,

    val classType: String

)

