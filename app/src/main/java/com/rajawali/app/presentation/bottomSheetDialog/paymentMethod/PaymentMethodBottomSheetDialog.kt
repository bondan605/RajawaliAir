package com.rajawali.app.presentation.bottomSheetDialog.paymentMethod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.R
import com.rajawali.app.databinding.BottomSheetDialogPaymentMethodBinding
import com.rajawali.app.util.Payment
import com.rajawali.core.domain.enums.PaymentMethodEnum
import com.rajawali.core.domain.model.PaymentMethodModel

class PaymentMethodBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogPaymentMethodBinding get() = _binding!!
    private var _binding: BottomSheetDialogPaymentMethodBinding? = null

    private val paymentMethod: PaymentMethodViewModel by navGraphViewModels(R.id.nav_booking)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            BottomSheetDialogPaymentMethodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here.

        setOnAllClickableListener()

        populatePaymentMethodView()
    }

    private fun populatePaymentMethodView() {
        Payment.paymentList.forEach {
            updatePaymentMethodView(it)
        }
    }

    private fun updatePaymentMethodView(paymentMethod: PaymentMethodModel) {
        val include = binding.root.findViewById<View>(paymentMethod.includeId)

        include.findViewById<TextView>(R.id.tv_payment_name).text = getString(paymentMethod.name)
        include.findViewById<ImageView>(R.id.iv_payment_logo).setImageResource(paymentMethod.logo)
    }

    private fun setOnAllClickableListener() {
        binding.btnExit.setOnClickListener {
            popBack()
        }

        binding.includeBcaTransferMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.BCA_TRANSFER)
            popBack()
        }

        binding.includeBniTransferMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.BNI_TRANSFER)
            popBack()
        }

        binding.includeBriTransferMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.BRI_TRANSFER)
            popBack()
        }

        binding.includeMandiriTransferMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.MANDIRI_TRANSFER)
            popBack()
        }

        binding.includeBcaVirtualMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.BCA_VIRTUAL)
            popBack()
        }

        binding.includeBniVirtualMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.BNI_VIRTUAL)
            popBack()
        }

        binding.includeBriVirtualMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.BRI_VIRTUAL)
            popBack()
        }

        binding.includeMandiriVirtualMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.MANDIRI_VIRTUAL)
            popBack()
        }

        binding.includeAlfamartMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.ALFAMART)
            popBack()
        }

        binding.includeIndomaretMethod.root.setOnClickListener {
            paymentMethod.setPaymentMethod(PaymentMethodEnum.INDOMARERT)
            popBack()
        }

        binding.btnApply.setOnClickListener {
            popBack()
        }
    }

    private fun popBack() {
        findNavController().popBackStack()
    }
}