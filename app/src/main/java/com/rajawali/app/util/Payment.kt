package com.rajawali.app.util

import com.rajawali.common_resource.R
import com.rajawali.core.domain.enums.PaymentMethodEnum
import com.rajawali.core.domain.model.PaymentMethodModel
import com.rajawali.core.domain.model.PopulatePaymentMethodModel

object Payment {
    val paymentList = listOf(
        PaymentMethodModel(
            name = R.string.tv_bca_transfer_label,
            logo = R.drawable.img_bca_logo,
            includeId = com.rajawali.app.R.id.include_bca_transfer_method
        ),
        PaymentMethodModel(
            name = R.string.tv_bri_transfer_label,
            logo = R.drawable.img_bri_logo,
            includeId = com.rajawali.app.R.id.include_bri_transfer_method
        ),
        PaymentMethodModel(
            name = R.string.tv_bni_transfer_label,
            logo = R.drawable.img_bni_logo,
            includeId = com.rajawali.app.R.id.include_bni_transfer_method
        ),
        PaymentMethodModel(
            name = R.string.tv_mandiri_transfer_label,
            logo = R.drawable.img_mandiri_logo,
            includeId = com.rajawali.app.R.id.include_mandiri_transfer_method
        ),
        PaymentMethodModel(
            name = R.string.tv_bca_virtual_label,
            logo = R.drawable.img_bca_logo,
            includeId = com.rajawali.app.R.id.include_bca_virtual_method
        ),
        PaymentMethodModel(
            name = R.string.tv_bri_virtual_label,
            logo = R.drawable.img_bri_logo,
            includeId = com.rajawali.app.R.id.include_bri_virtual_method
        ),
        PaymentMethodModel(
            name = R.string.tv_bni_virtual_label,
            logo = R.drawable.img_bni_logo,
            includeId = com.rajawali.app.R.id.include_bni_virtual_method
        ),
        PaymentMethodModel(
            name = R.string.tv_mandiri_virtual_label,
            logo = R.drawable.img_mandiri_logo,
            includeId = com.rajawali.app.R.id.include_mandiri_virtual_method
        ),
        PaymentMethodModel(
            name = R.string.tv_alfamart_label,
            logo = R.drawable.img_alfamart_logo,
            includeId = com.rajawali.app.R.id.include_alfamart_method
        ),
        PaymentMethodModel(
            name = R.string.tv_indomaret_label,
            logo = R.drawable.img_indomaret_logo,
            includeId = com.rajawali.app.R.id.include_indomaret_method
        ),
    )

    val populatePaymentList = listOf(
        PopulatePaymentMethodModel(
            name = R.string.tv_bca_transfer_label,
            logo = R.drawable.img_bca_logo,
            method = PaymentMethodEnum.BCA_TRANSFER
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_bri_transfer_label,
            logo = R.drawable.img_bri_logo,
            method = PaymentMethodEnum.BRI_TRANSFER
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_bni_transfer_label,
            logo = R.drawable.img_bni_logo,
            method = PaymentMethodEnum.BNI_TRANSFER
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_mandiri_transfer_label,
            logo = R.drawable.img_mandiri_logo,
            method = PaymentMethodEnum.MANDIRI_TRANSFER
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_bca_virtual_label,
            logo = R.drawable.img_bca_logo,
            method = PaymentMethodEnum.BCA_VIRTUAL
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_bri_virtual_label,
            logo = R.drawable.img_bri_logo,
            method = PaymentMethodEnum.BRI_VIRTUAL
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_bni_virtual_label,
            logo = R.drawable.img_bni_logo,
            method = PaymentMethodEnum.BNI_VIRTUAL
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_mandiri_virtual_label,
            logo = R.drawable.img_mandiri_logo,
            method = PaymentMethodEnum.MANDIRI_VIRTUAL
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_alfamart_label,
            logo = R.drawable.img_alfamart_logo,
            method = PaymentMethodEnum.ALFAMART
        ),
        PopulatePaymentMethodModel(
            name = R.string.tv_indomaret_label,
            logo = R.drawable.img_indomaret_logo,
            method = PaymentMethodEnum.INDOMARERT
        ),
    )
}