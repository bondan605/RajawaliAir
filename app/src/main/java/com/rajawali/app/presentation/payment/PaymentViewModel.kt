package com.rajawali.app.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.PayReservationModel
import com.rajawali.core.domain.model.PaymentRecordModel
import com.rajawali.core.domain.model.PaymentViewState
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.domain.usecase.PayReservationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel(private val createPayment : PayReservationUseCase) : ViewModel() {

    private val _paymentViewState = MutableStateFlow(PaymentViewState())
    val paymentViewState: StateFlow<PaymentViewState> = _paymentViewState

    fun updateReservationId(id : String) {
        _paymentViewState.value = paymentViewState.value.copy(reservationId = id)
    }
    fun updatePaymentMethod(method : String) {
        _paymentViewState.value = paymentViewState.value.copy(paymentMethod = method)
    }

    fun payTicket(reservationId: String, paymentMethod: String) : LiveData<UCResult<PaymentRecordModel>> {
        val payment =
            PayReservationModel(
                reservationId = reservationId,
                paymentMethod = paymentMethod
            )

        return createPayment.payReservation(payment).asLiveData()
    }
}