package com.rajawali.app.presentation.bottomSheetDialog.paymentMethod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.PaymentMethodEnum

@Suppress("RemoveExplicitTypeArguments")
class PaymentMethodViewModel : ViewModel() {

    private val _paymentMethod = MutableLiveData<PaymentMethodEnum>(PaymentMethodEnum.BCA_VIRTUAL)
    val paymentMethod : LiveData<PaymentMethodEnum> = _paymentMethod

    fun setPaymentMethod(method : PaymentMethodEnum) {
        _paymentMethod.value = method
    }
}