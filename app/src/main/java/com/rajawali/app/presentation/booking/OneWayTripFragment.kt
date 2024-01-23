package com.rajawali.app.presentation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentOneWayTripBinding
import com.rajawali.app.presentation.bottomSheetDialog.passenger.PassengerViewModel
import com.rajawali.app.presentation.pickCity.AirportsViewModel
import com.rajawali.app.util.DateFormat
import com.rajawali.app.util.DateFormat.formatToIndonesiaLanguage
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.AirportTypeEnum
import com.rajawali.core.domain.enums.PassengerClassEnum

class OneWayTripFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentOneWayTripBinding? = null

    private val airportsViewModel: AirportsViewModel by activityViewModels()
    private val passengerViewModel: PassengerViewModel by activityViewModels()
    private val tripViewModel: TripViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneWayTripBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do something here.

        setDeparture()
        setArriving()

        arrivingOnClick()
        departureOnClick()

        handleRoundTripSwitch()
        updateReturnDateViewBasedOnTripType()

        setDate()
        //set current date for tv departure date and tv return date
        setCurrentDate()

        passengerOnClick()
        updatePassengerDisplay()
    }

    private fun setDeparture() {
        val include = binding.includeBookingForm
        airportsViewModel.departureAirport.observe(viewLifecycleOwner) {
            include.tvDepartureCity.text =
                getString(R.string.tv_departure_city_name_label, it.city, it.cityCode)
        }
    }

    private fun setArriving() {
        val include = binding.includeBookingForm
        airportsViewModel.arrivingAirport.observe(viewLifecycleOwner) {
            include.tvArrivingCity.text =
                getString(R.string.tv_arrive_city_name_label, it.city, it.cityCode)
        }
    }

    private fun departureOnClick() {
        binding.includeBookingForm.llDeparture.setOnClickListener {
            val destination =
                OneWayTripFragmentDirections.actionOneWayTripFragmentToAirportBottomSheetDialog(
                    AirportTypeEnum.DEPARTURE
                )
            findNavController().safeNavigate(destination)
        }
    }

    private fun arrivingOnClick() {
        binding.includeBookingForm.llArriving.setOnClickListener {
            val destination =
                OneWayTripFragmentDirections.actionOneWayTripFragmentToAirportBottomSheetDialog(
                    AirportTypeEnum.ARRIVING
                )
            findNavController().safeNavigate(destination)
        }
    }

    private fun handleRoundTripSwitch() {
        binding.includeBookingForm.btnSwitchRoundTrip.setOnCheckedChangeListener { _, isRoundTrip ->
            tripViewModel.isRoundTrip(isRoundTrip)
        }
    }

    private fun updateReturnDateViewBasedOnTripType() {
        tripViewModel.roundTrip.observe(viewLifecycleOwner) { isRoundTrip ->
            when (isRoundTrip) {
                true ->
                    binding.includeBookingForm.llReturnDate.visibility = View.VISIBLE

                false ->
                    binding.includeBookingForm.llReturnDate.visibility = View.GONE
            }
        }
    }

    private fun setDate() {
        tripViewModel.roundTrip.observe(viewLifecycleOwner) { roundTrip ->
            when (roundTrip) {
                true -> {
                    binding.includeBookingForm.llDepartureDate.setOnClickListener {
                        dateRangePicker()
                    }

                    binding.includeBookingForm.llReturnDate.setOnClickListener {
                        dateRangePicker()
                    }
                }

                false -> {
                    binding.includeBookingForm.llDepartureDate.setOnClickListener {
                        openDatePicker(DEPARTURE_DATE, getString(R.string.dp_departure_label))
                    }
                }
            }
        }
    }

    private fun dateRangePicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(R.string.dp_range_date_label)
                .setSelection(
                    Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()
        dateRangePicker.show(requireActivity().supportFragmentManager, RANGE_DATE)

        dateRangePicker.addOnPositiveButtonClickListener {

            val firstLocalDate = DateFormat.longToDate(it.first)
            val firstFormattedDate = formatToIndonesiaLanguage(firstLocalDate)

            val secondLocalDate = DateFormat.longToDate(it.second)
            val secondFormattedDate = formatToIndonesiaLanguage(secondLocalDate)


            binding.includeBookingForm.tvDepartureDateValue.text = firstFormattedDate
            binding.includeBookingForm.tvReturnDateValue.text = secondFormattedDate
        }
    }

    private fun openDatePicker(tag: String, title: String) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .build()
        datePicker.show(requireActivity().supportFragmentManager, tag)

        datePicker.addOnPositiveButtonClickListener {
            val localDate = DateFormat.longToDate(it)
            val formattedDate = formatToIndonesiaLanguage(localDate)

            binding.includeBookingForm.tvDepartureDateValue.text = formattedDate
        }
    }

    private fun setCurrentDate() {
        binding.includeBookingForm.tvDepartureDateValue.text =
            formatToIndonesiaLanguage(DateFormat.currentDate)

        binding.includeBookingForm.tvReturnDateValue.text =
            formatToIndonesiaLanguage(DateFormat.currentDate)
    }

    private fun passengerOnClick() {
        binding.includeBookingForm.llPassenger.setOnClickListener {
            val destination =
                OneWayTripFragmentDirections.actionOneWayTripFragmentToPassengerBottomSheetDialog()
            findNavController().safeNavigate(destination)
        }
    }


    private fun getPassengerCategoryText(): String {
        var text = ""

        passengerViewModel.adultPassengerCount.observe(viewLifecycleOwner) {
            if (it > 0)
                text = isPassengerExist(text, it, R.string.tv_passenger_adult_category)
        }

        passengerViewModel.childPassengerCount.observe(viewLifecycleOwner) {
            if (it > 0)
                text = isPassengerExist(text, it, R.string.tv_passenger_child_category)
        }

        passengerViewModel.infantPassengerCount.observe(viewLifecycleOwner) {
            if (it > 0)
                text = isPassengerExist(
                    text,
                    it,
                    R.string.tv_passenger_infant_category
                )
        }
        return text
    }

    private fun isPassengerExist(
        initialText: String,
        count: Int,
        @StringRes template: Int
    ): String {
        var text = initialText.addTextComa()

        if (count > 0)
            text += getString(template, count)

        return text
    }


    private fun getPassengerClassText(): String {
        var text = ""

        passengerViewModel.passengerClass.observe(viewLifecycleOwner) {

            text += when (it) {
                PassengerClassEnum.ECONOMY ->
                    getString(R.string.tv_passenger_economy_class)

                PassengerClassEnum.BUSINESS ->
                    getString(R.string.tv_passenger_economy_class)

                PassengerClassEnum.FIRST ->
                    getString(R.string.tv_passenger_first_class)

                else -> {}
            }
        }
        return text
    }

    private fun updatePassengerDisplay() {
        passengerViewModel.onButtonDoneClickListener.observe(viewLifecycleOwner) { isDone ->
            if (isDone) {
                val passengerCategory = getPassengerCategoryText()
                val passengerClass = getPassengerClassText()
                val passengerCategoryAndCabinClass =
                    passengerCategory.addTextComa() + passengerClass

                Toast.makeText(activity, passengerCategoryAndCabinClass, Toast.LENGTH_SHORT).show()

                binding.includeBookingForm.tvPassengerValue.text = passengerCategoryAndCabinClass

                passengerViewModel.setOnButtonDoneClickListener(false)

            }
        }
    }

    private fun String.addTextComa(): String =
        if (this.isNotEmpty())
            "$this, "
        else
            this

    companion object {
        const val DEPARTURE_DATE = "DepartureDate"
//        const val RETURN_DATE = "ReturnDate"
        const val RANGE_DATE = "RangeDate"
    }
}