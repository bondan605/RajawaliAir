package com.rajawali.app.presentation.paymentPending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajawali.core.domain.enums.PaymentStatusEnum
import com.rajawali.core.domain.model.PaymentStatus
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.GetReservationByIdUseCase
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class PaymentPendingViewModel(private val paymentWaiting: GetReservationByIdUseCase) : ViewModel() {

    private val _paymentApprove = MutableLiveData<PaymentStatus>()
    val paymentApprove: LiveData<PaymentStatus> = _paymentApprove

    private val _paymentFailed = MutableLiveData<Boolean>(false)
    val paymentFailed: LiveData<Boolean> = _paymentFailed


    fun isApprove(id: String) {
        viewModelScope.launch {
            var status = paymentApprove.value?.status ?: ""

            while (status != Constant.PURCHASE_SUCCESS) {
                val payment = paymentWaiting.getReservation(id)
                when (payment) {
                    is CommonResult.Error -> {
                        _paymentFailed.postValue(true)
                        return@launch
                    }

                    is CommonResult.Success -> {
                        status = payment.data.paymentStatus
                        Timber.d("isApprove : $status")

                        if (status != Constant.PURCHASE_SUCCESS) {
                            val model = PaymentStatus(status = status, isSuccess = false)
                            _paymentApprove.postValue(model)
                        } else {
                            val model = PaymentStatus(status = status, isSuccess = true)
                            _paymentApprove.postValue(model)
                        }
                    }

                }
                delay(5000)
            }
        }
    }

    private fun getPaymentStatus(status: String): PaymentStatusEnum {
        val statusEnum = DataMapper.paymentStatusStringToPaymentStatusEnum(status)
        Timber.d("getPaymentStatus : $status , $statusEnum")

        return statusEnum
    }
}