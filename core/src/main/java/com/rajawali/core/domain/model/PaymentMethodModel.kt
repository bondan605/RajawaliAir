package com.rajawali.core.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rajawali.core.R
import com.rajawali.core.domain.enums.PaymentMethodEnum

data class PaymentMethodModel(
    @StringRes val name : Int,
    @DrawableRes val logo : Int,
    val includeId : Int,
)

data class PopulatePaymentMethodModel(
    @StringRes val name : Int,
    @DrawableRes val logo : Int,
    val method : PaymentMethodEnum,
)
