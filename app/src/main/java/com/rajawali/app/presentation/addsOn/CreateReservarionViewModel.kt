package com.rajawali.app.presentation.addsOn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.ReservationModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.domain.usecase.CreateReservationUseCase
import timber.log.Timber

class CreateReservationViewModel(private val createReservation : CreateReservationUseCase) : ViewModel() {

    fun createReservation(data : CreateReservationModel) : LiveData<UCResult<ReservationModel>> {
        Timber.d("CreateReservation : $data")
        return createReservation.createReservation(data).asLiveData()
    }
}