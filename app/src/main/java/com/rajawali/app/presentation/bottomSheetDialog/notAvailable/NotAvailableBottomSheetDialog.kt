package com.rajawali.app.presentation.bottomSheetDialog.notAvailable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.NavBookingDirections
import com.rajawali.app.R
import com.rajawali.app.databinding.NotAvailableBottomSheetDialogBinding
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.NotAvailableEnum

class NotAvailableBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: NotAvailableBottomSheetDialogBinding get() = _binding!!
    private var _binding: NotAvailableBottomSheetDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotAvailableBottomSheetDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here
        populateView()

        setOnBtnNotAvailableClicked()
    }

    private fun populateView() {
        val included = binding.includeNotAvailable

        included.tvNoAvailableLabel.text = getString(setTvNotAvailableLabelText())
        included.tvNoAvailableDescription.text = getString(setTvNotAvailableDescriptionText())
        included.btnNoAvailable.text = getString(setBtnNotAvailableDescriptionText())
    }

    private fun setTvNotAvailableLabelText(): Int =
        when (getNotAvailableType()) {
            NotAvailableEnum.PAYMENT ->
                R.string.tv_payment_not_available_anymore_label

            NotAvailableEnum.SEAT ->
                R.string.tv_seats_not_available_anymore_label

            NotAvailableEnum.LOGIN ->
                R.string.tv_not_login_label

        }

    private fun setTvNotAvailableDescriptionText(): Int =
        when (getNotAvailableType()) {
            NotAvailableEnum.PAYMENT ->
                R.string.tv_payment_not_available_anymore_description

            NotAvailableEnum.SEAT ->
                R.string.tv_seats_not_available_anymore_description

            NotAvailableEnum.LOGIN ->
                R.string.tv_not_login_description
        }

    private fun setBtnNotAvailableDescriptionText(): Int =
        when (getNotAvailableType()) {
            NotAvailableEnum.PAYMENT ->
                R.string.btn_payment_not_available_anymore_label

            NotAvailableEnum.SEAT ->
                R.string.btn_seats_not_available_anymore_label

            NotAvailableEnum.LOGIN ->
                R.string.btn_login_label
        }

    private fun setOnBtnNotAvailableClicked() {
        binding.includeNotAvailable.btnNoAvailable.setOnClickListener {
            findNavController().safeNavigate(destination())
        }
    }

    private fun destination(): NavDirections =
        when (getNotAvailableType()) {
            NotAvailableEnum.PAYMENT ->
                NavBookingDirections.actionGlobalHomePageFragment()

            NotAvailableEnum.SEAT ->
                NotAvailableBottomSheetDialogDirections.actionNotAvailableBottomSheetDialogToChooseTicketFragment()

            NotAvailableEnum.LOGIN ->
                NotAvailableBottomSheetDialogDirections.actionNotAvailableBottomSheetDialogToLoginFragment()
        }

    private fun getNotAvailableType(): NotAvailableEnum =
        NotAvailableBottomSheetDialogArgs.fromBundle(arguments as Bundle).type

}