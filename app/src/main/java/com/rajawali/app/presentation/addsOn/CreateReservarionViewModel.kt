package com.rajawali.app.presentation.addsOn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.ReservationModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.reservation.CreateReservationUseCase
import timber.log.Timber

class CreateReservationViewModel(private val createReservation : CreateReservationUseCase) : ViewModel() {

    fun createReservation(data : CreateReservationModel) : LiveData<CommonResult<ReservationModel>> {
        Timber.d("CreateReservation : $data")
        return createReservation.createReservation(data).asLiveData()
    }
}