package com.rajawali.app.presentation.bottomSheetDialog.notLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.R
import com.rajawali.app.databinding.NotAvailableBottomSheetDialogBinding
import com.rajawali.app.util.NavigationUtils.safeNavigate

class NotLoginSheetDialog : BottomSheetDialogFragment() {
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
        R.string.tv_not_login_label

    private fun setTvNotAvailableDescriptionText(): Int =
        R.string.tv_not_login_description

    private fun setBtnNotAvailableDescriptionText(): Int =
        R.string.btn_login_label

    private fun setOnBtnNotAvailableClicked() {
        binding.includeNotAvailable.btnNoAvailable.setOnClickListener {
            findNavController().safeNavigate(destination())
        }
    }

    private fun destination(): NavDirections =
        NotLoginSheetDialogDirections.actionNotLoginSheetDialogToLoginFragment()

}