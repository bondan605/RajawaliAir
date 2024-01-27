package com.rajawali.app.presentation.bottomSheetDialog.flightDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.databinding.BottomSheetDialogFlightDetailBinding

class FlightDetailBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogFlightDetailBinding get() = _binding!!
    private var _binding: BottomSheetDialogFlightDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogFlightDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here
    }
}