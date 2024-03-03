package com.rajawali.core.domain.usecase.addOns

import com.rajawali.core.domain.model.SeatsModel

class FilterSeatsUseCase {

    fun filterSeat(seats: List<SeatsModel>): List<SeatsModel> =
        seats.filter { it.isAvailable }
}