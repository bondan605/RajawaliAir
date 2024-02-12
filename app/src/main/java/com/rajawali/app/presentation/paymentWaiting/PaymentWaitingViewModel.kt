package com.rajawali.app.presentation.paymentWaiting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.enums.PaymentStatusEnum
import com.rajawali.core.domain.model.ReservationByIdModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.domain.usecase.GetReservationByIdUseCase
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import timber.log.Timber

class PaymentWaitingViewModel(private val useCase: GetReservationByIdUseCase) : ViewModel() {

    private val _transferDropdown = MutableLiveData<Boolean>(false)
    val transferDropdown: LiveData<Boolean> = _transferDropdown

    private val _internetDropdown = MutableLiveData<Boolean>(false)
    val internetDropdown: LiveData<Boolean> = _internetDropdown

    private val _mobileDropdown = MutableLiveData<Boolean>(false)
    val mobileDropdown: LiveData<Boolean> = _mobileDropdown

    fun getPaymentStatus(status : String) : PaymentStatusEnum {
        val statusEnum = DataMapper.paymentStatusStringToPaymentStatusEnum(status)
        Timber.d("getPaymentStatus : $status , $statusEnum")

        return statusEnum
    }

    fun getReservationById(id : String) : LiveData<UCResult<ReservationByIdModel>> = useCase.getReservation(id).asLiveData()

    fun setTransferDropdown() {
        val state = transferDropdown.value ?: false
        _transferDropdown.value = !state
    }

    fun setInternetDropdown() {
        val state = internetDropdown.value ?: false
        _internetDropdown.value = !state
    }

    fun setMobileDropdown() {
        val state = mobileDropdown.value ?: false
        _mobileDropdown.value = !state
    }
}