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
import com.rajawali.app.util.NavigationUtils.safeNavigateUsingID
import com.rajawali.core.domain.enums.AirportTypeEnum
import com.rajawali.core.domain.enums.PassengerClassEnum
import com.rajawali.core.domain.enums.TripValueEnum

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
        setDefaultDate()

        passengerOnClick()
        updatePassengerDisplay()

        searchFlight()
    }

    private fun searchFlight() {
        binding.includeBookingForm.btnSearch.setOnClickListener {
            var departureId = ""
            var departureDate = ""
            var returnDate = ""
            var destinationId = ""
            var seatType = PassengerClassEnum.NULL
            var adultPassenger = 0
            var childPassenger = 0
            var infantPassenger = 0

            airportsViewModel.departureAirport.observe(viewLifecycleOwner) {
                departureId = it.id
            }

            airportsViewModel.arrivingAirport.observe(viewLifecycleOwner) {
                destinationId = it.id
            }

            tripViewModel.departureDate.observe(viewLifecycleOwner) {
                departureDate = it
            }

            tripViewModel.returnDate.observe(viewLifecycleOwner) {
                returnDate = it
            }

            passengerViewModel.adultPassengerCount.observe(viewLifecycleOwner) {
                adultPassenger = it
            }

            passengerViewModel.childPassengerCount.observe(viewLifecycleOwner) {
                childPassenger = it
            }

            passengerViewModel.infantPassengerCount.observe(viewLifecycleOwner) {
                infantPassenger = it
            }

            passengerViewModel.passengerClass.observe(viewLifecycleOwner) {
                seatType = PassengerClassEnum.ECONOMY
            }

            isValueEmpty(
                departureId,
                departureDate,
                destinationId,
                seatType,
                adultPassenger,
                childPassenger,
                infantPassenger,
            )

            tripViewModel.isValueEmpty.observe(viewLifecycleOwner) { isEmpty ->
                isEmpty.map {
                    when (it) {
                        TripValueEnum.DEPARTURE_CITY -> {
                            Toast.makeText(activity, "From is empty", Toast.LENGTH_SHORT)
                                .show()
                        }

                        TripValueEnum.DEPARTURE_DATE ->
                            Toast.makeText(activity, "Departure date is empty", Toast.LENGTH_SHORT)
                                .show()

                        TripValueEnum.DESTINATION_CITY ->
                            Toast.makeText(activity, "To is empty", Toast.LENGTH_SHORT)
                                .show()

                        TripValueEnum.SEAT_TYPE ->
                            Toast.makeText(
                                activity,
                                "Passenger class is not yet choose",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        TripValueEnum.PASSENGER -> {
                            Toast.makeText(activity, "Passenger is empty", Toast.LENGTH_SHORT)
                                .show()
                        }

                        TripValueEnum.NULL -> {

                            tripViewModel.roundTrip.observe(viewLifecycleOwner) { isRoundTrip ->
                                if (isRoundTrip) {
                                    val departureTrip = tripViewModel.setDepartureTicket(
                                        departureCityCode = departureId,
                                        departureDate = departureDate,
                                        destinationCityCode = destinationId,
                                        seatType = seatType,
                                        adultPassenger = adultPassenger,
                                        childPassenger = childPassenger,
                                        infantPassenger = infantPassenger,
                                    )
                                    val returnTrip = tripViewModel.setReturnTicket(
                                        departureCityCode = destinationId,
                                        returnDate = returnDate,
                                        destinationCityCode = departureId,
                                        seatType = seatType,
                                        adultPassenger = adultPassenger,
                                        childPassenger = childPassenger,
                                        infantPassenger = infantPassenger,
                                    )
                                    val bundle = Bundle()

                                    bundle.putBoolean(ROUND_TRIP, isRoundTrip)
                                    bundle.putParcelable(DEPARTURE, departureTrip)
                                    bundle.putParcelable(RETURN, returnTrip)

                                    findNavController().safeNavigateUsingID(
                                        R.id.action_oneWayTripFragment_to_chooseTicketFragment,
                                        bundle
                                    )

                                }
                                else {
                                    val departureTrip = tripViewModel.setDepartureTicket(
                                        departureCityCode = departureId,
                                        departureDate = departureDate,
                                        destinationCityCode = destinationId,
                                        seatType = seatType,
                                        adultPassenger = adultPassenger,
                                        childPassenger = childPassenger,
                                        infantPassenger = infantPassenger,
                                    )
                                    val bundle = Bundle()

                                    bundle.putBoolean(ROUND_TRIP, isRoundTrip)
                                    bundle.putParcelable(DEPARTURE, departureTrip)

                                    findNavController().safeNavigateUsingID(
                                        R.id.action_oneWayTripFragment_to_chooseTicketFragment,
                                        bundle
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isValueEmpty(
        departureId: String,
        departureDate: String,
        destinationId: String,
        seatType: PassengerClassEnum,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
    ) {
        tripViewModel.isValueEmpty(
            departureCityCode = departureId,
            departureDate = departureDate,
            destinationCityCode = destinationId,
            seatType = seatType,
            adultPassenger = adultPassenger,
            childPassenger = childPassenger,
            infantPassenger = infantPassenger
        )
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

            val departureLocalDate = DateFormat.longToLocalDate(it.first)
            val departureFormattedDate = formatToIndonesiaLanguage(departureLocalDate)

            val returnLocalDate = DateFormat.longToLocalDate(it.second)
            val returnFormattedDate = formatToIndonesiaLanguage(returnLocalDate)

            binding.includeBookingForm.tvDepartureDateValue.text = departureFormattedDate
            binding.includeBookingForm.tvReturnDateValue.text = returnFormattedDate

            tripViewModel.setDepartureDate(departureLocalDate)
            tripViewModel.setReturnDate(returnLocalDate)
        }
    }

    private fun openDatePicker(tag: String, title: String) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .build()
        datePicker.show(requireActivity().supportFragmentManager, tag)

        datePicker.addOnPositiveButtonClickListener {
            val localDate = DateFormat.longToLocalDate(it)
            val formattedDate = formatToIndonesiaLanguage(localDate)

            binding.includeBookingForm.tvDepartureDateValue.text = formattedDate

            tripViewModel.setDepartureDate(localDate)
        }
    }


    //set the default date
    /* I only set it for departure date in Trip View Model because by default is not a round trip*/
    private fun setDefaultDate() {
        val todayDate = DateFormat.currentDate

        binding.includeBookingForm.tvDepartureDateValue.text =
            formatToIndonesiaLanguage(todayDate)

        binding.includeBookingForm.tvReturnDateValue.text =
            formatToIndonesiaLanguage(todayDate)

        tripViewModel.setDepartureDate(todayDate)
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
                PassengerClassEnum.ECONOMY -> {
                    getString(R.string.tv_passenger_economy_class)
                }

                PassengerClassEnum.BUSINESS -> {
                    getString(R.string.tv_passenger_business_class)
                }

                PassengerClassEnum.FIRST -> {
                    getString(R.string.tv_passenger_first_class)
                }

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
        const val ROUND_TRIP = "isRoundTrip"
        const val DEPARTURE = "departure"
        const val RETURN = "return"
    }
}