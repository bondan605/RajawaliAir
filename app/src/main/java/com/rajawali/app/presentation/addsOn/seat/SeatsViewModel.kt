package com.rajawali.app.presentation.addsOn.seat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.AvailableSeatsModel
import com.rajawali.core.domain.model.SeatsModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.addOns.FilterSeatsUseCase
import com.rajawali.core.domain.usecase.addOns.GetAvailableSeatsUseCase

class SeatsViewModel(
    private val getAvailableSeats: GetAvailableSeatsUseCase,
    private val filterSeats: FilterSeatsUseCase
) : ViewModel() {
    fun getAvailableSeats(
        id: String,
        seatType: String
    ): LiveData<CommonResult<AvailableSeatsModel>> =
        getAvailableSeats.getAvailableSeats(id, seatType).asLiveData()


    fun filterSeat(seats: List<SeatsModel>): List<SeatsModel> =
        filterSeats.filterSeat(seats)
}