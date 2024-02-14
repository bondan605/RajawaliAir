package com.rajawali.app.presentation.addsOn.seat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.AvailableSeatsModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.GetAvailableSeatsUseCase

class SeatsViewModel(private val getAvailableSeats: GetAvailableSeatsUseCase) : ViewModel() {
    fun getAvailableSeats(
        id: String,
        seatType: String
    ): LiveData<CommonResult<AvailableSeatsModel>> =
        getAvailableSeats.getAvailableSeats(id, seatType).asLiveData()

}