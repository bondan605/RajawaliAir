package com.rajawali.app.presentation.bottomSheetDialog.notLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.R
import com.rajawali.app.databinding.BottomSheetDialogNotAvailableBinding
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.NotAvailableEnum

class NotLoginSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogNotAvailableBinding get() = _binding!!
    private var _binding: BottomSheetDialogNotAvailableBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogNotAvailableBinding.inflate(layoutInflater, container, false)
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
        included.ivNoAvailable.setImageResource(setIvBackground())
    }

    private fun setTvNotAvailableLabelText(): Int =
        when (getNotAvailableType()) {
            NotAvailableEnum.LOGIN ->
                R.string.tv_not_login_label

            NotAvailableEnum.IMPLEMENT ->
                R.string.tv_not_implemented_label

            else ->
                R.string.tv_null_label
        }

    private fun setTvNotAvailableDescriptionText(): Int =
        when (getNotAvailableType()) {
            NotAvailableEnum.LOGIN ->
                R.string.tv_not_login_description

            NotAvailableEnum.IMPLEMENT ->
                R.string.tv_not_implemented_description

            else ->
                R.string.tv_null_description
        }


    private fun setBtnNotAvailableDescriptionText(): Int =
        when (getNotAvailableType()) {
            NotAvailableEnum.LOGIN ->
                R.string.btn_login_label

            NotAvailableEnum.IMPLEMENT ->
                R.string.btn_not_implemented_label

            else ->
                R.string.btn_null_label
        }


    private fun setIvBackground(): Int =
        R.drawable.ic_questions_amico

    private fun setOnBtnNotAvailableClicked() {
        binding.includeNotAvailable.btnNoAvailable.setOnClickListener {
            navigate()
        }
    }

    private fun navigate() {
        when (getNotAvailableType()) {
            NotAvailableEnum.LOGIN ->
                findNavController().safeNavigate(destination())

            else ->
                findNavController().popBackStack()
        }
    }

    private fun destination(): NavDirections =
        NotLoginSheetDialogDirections.actionNotLoginSheetDialogToLoginFragment()

    private fun getNotAvailableType(): NotAvailableEnum =
        NotLoginSheetDialogArgs.fromBundle(arguments as Bundle).type

}