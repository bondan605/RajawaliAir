package com.rajawali.app.presentation.selectedTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentSelectedTicketBinding
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.AppUtils
import com.rajawali.app.util.AppUtils.isPassengerExist
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.model.Insurance
import com.rajawali.core.util.DateFormat

class SelectedTicketFragment : Fragment() {
    private var _binding: FragmentSelectedTicketBinding? = null
    private val binding get() = _binding!!

    private val ticketViewModel: TicketViewModel by activityViewModels()
    private val addsOnViewModel: TravelAddsOnViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedTicketBinding.inflate(layoutInflater, container, false)
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

        onButtonSelected()
    }

    private fun onButtonSelected() {
        binding.btnSelect.setOnClickListener {
            onButtonSelectListener()
        }

        binding.safeBtnSelect.setOnClickListener {
            onButtonSelectListener()
        }
    }

    private fun onButtonSelectListener(isSafeSelected: Boolean = false) {
        val destination =
            SelectedTicketFragmentDirections
                .actionSelectedTicketFragmentToDetailsInformationFragment()
//                .actionId
//        val departure = getFlightBundle(ChooseTicketFragment.DEPARTURE)
//        val bundle = Bundle()

//        bundle.putParcelable(ChooseTicketFragment.DEPARTURE, departure)

//        findNavController().safeNavigateUsingID(destination, bundle)

        if (isSafeSelected)
            addsOnViewModel.setTravelInsurance()

        findNavController().safeNavigate(destination)
    }

    //TODO return ticket. No design yet.
    private fun populateView() {
//        val departure = getFlightBundle(ChooseTicketFragment.DEPARTURE)
//        val preferableDeparture = getFindTicketBundle(ChooseTicketFragment.PREFERABLE_DEPARTURE)

        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { preferableDeparture ->
            val passenger = setPassengerToDisplay(
                preferableDeparture?.adultPassenger,
                preferableDeparture?.childPassenger,
                preferableDeparture?.infantPassenger
            )

            binding.passenger.text = passenger
        }

        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { departure ->

            val fullDate = DateFormat.formatToFullDateWithDay(departure.departureDate)
            val departureDateAndMonth = DateFormat.formatDateAndMonthOnly(departure.departureDate)
            val arrivalDateAndMonth = DateFormat.formatDateAndMonthOnly(departure.arrivalDate)
            val flightLengthHour =
                DateFormat.calculateTimeDifference(departure.departureTime, departure.arrivalTime)

            binding.departure.text = departure.sourceAirport.city
            binding.arrive.text = departure.destinationAirport.city

            binding.departureTime.text = departure.departureTime
            binding.arriveTime.text = departure.arrivalTime
            binding.tvDepartureDateMonth.text = departureDateAndMonth
            binding.tvArriveDateMonth.text = arrivalDateAndMonth

            binding.tvDepartureCityNameLabel.text = getString(
                R.string.tv_city_code,
                departure.sourceAirport.city,
                departure.sourceAirport.cityCode
            )
            binding.tvDepartureAirportName.text = departure.sourceAirport.airport

            binding.tvArriveCityNameLabel.text = getString(
                R.string.tv_city_code,
                departure.destinationAirport.city,
                departure.destinationAirport.cityCode
            )
            binding.tvArriveAirportName.text = departure.destinationAirport.airport

            binding.ticketDetail.text = getString(
                R.string.tv_ticket_detail,
                departure.airplane.airplaneCode,
                AppUtils.capitalize(departure.classType),
                flightLengthHour
            )

            binding.tvDateDeparture.text = fullDate

            //safe section.
            val safeTotalPrice = departure.totalPrice + Insurance.PriceList.travelInsurance
            binding.priceTicket.text = getString(R.string.tv_price_ticket, departure.seatPrice)
            binding.totalPrice.text = getString(R.string.tv_total_price, safeTotalPrice)
            binding.point.text = getString(R.string.tv_point, departure.points)
        }
    }

    private fun setPassengerToDisplay(
        initialAdultPassenger: Int?,
        initialChildPassenger: Int?,
        initialInfantPassenger: Int?
    ): String {
        var text = ""

        text = activity.isPassengerExist(text, initialAdultPassenger, PassengerCategoryEnum.ADULT)
        text = activity.isPassengerExist(text, initialChildPassenger, PassengerCategoryEnum.ADULT)
        text = activity.isPassengerExist(text, initialInfantPassenger, PassengerCategoryEnum.ADULT)

        return text
    }


    private fun getFlightBundle(tag: String): FlightModel? =
        arguments?.getParcelable(tag)

    private fun getFindTicketBundle(tag: String): FindTicketModel? =
        arguments?.getParcelable(tag)

}