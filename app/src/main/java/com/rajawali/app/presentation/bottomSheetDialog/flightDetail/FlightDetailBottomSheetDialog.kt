package com.rajawali.app.presentation.bottomSheetDialog.flightDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.R
import com.rajawali.app.databinding.BottomSheetDialogFlightDetailBinding
import com.rajawali.app.util.AppUtils
import com.rajawali.app.util.DateFormat
import com.rajawali.core.domain.model.FlightModel

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

        setTicketView()
        dismissBottomSheet()
    }

    private fun dismissBottomSheet() {
        binding.btnContinue.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setTicketView() {
        val ticket: FlightModel =
            FlightDetailBottomSheetDialogArgs.fromBundle(arguments as Bundle).ticket

        binding.tvDepartingCity.text = getString(
            R.string.tv_city_code,
            ticket.sourceAirport.city,
            ticket.sourceAirport.cityCode
        )
        binding.tvArrivingCity.text = getString(
            R.string.tv_city_code,
            ticket.destinationAirport.city,
            ticket.destinationAirport.cityCode
        )
        binding.tvDepartingAirport.text = ticket.sourceAirport.airport
        binding.tvArrivingAirport.text = ticket.destinationAirport.airport
        binding.tvDepartingCityCodeTime.text = getString(
            R.string.tv_city_code_time,
            ticket.sourceAirport.city,
            ticket.sourceAirport.cityCode,
            ticket.departureTime
        )
        binding.tvArrivingCityCodeTime.text = getString(
            R.string.tv_city_code_time,
            ticket.destinationAirport.city,
            ticket.destinationAirport.cityCode,
            ticket.arrivalTime
        )
        binding.tvFlightNumber.text = getString(
            R.string.tv_flight_number,
            ticket.airplane.airplaneCode,
            AppUtils.capitalize(ticket.classType)
        )
        binding.tvFlightDate.text = DateFormat.formatToFullDateWithDay(ticket.departureDate)

        binding.tvTotalPrice.text = getString(R.string.tv_total_price, ticket.totalPrice)

        binding.tvTotalPointGain.text = getString(R.string.tv_point_gain, ticket.points)
    }
}