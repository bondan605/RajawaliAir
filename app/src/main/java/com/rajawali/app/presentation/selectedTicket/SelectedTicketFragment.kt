package com.rajawali.app.presentation.selectedTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentSelectedTicketBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.AppUtils
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.model.Insurance
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel
import com.rajawali.core.util.DateFormat

class SelectedTicketFragment : Fragment() {
    private var _binding: FragmentSelectedTicketBinding? = null
    private val binding get() = _binding!!

//    private val ticketViewModel: TicketViewModel by activityViewModels()
//    private val addsOnViewModel: TravelAddsOnViewModel by activityViewModels()
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val addsOnViewModel: TravelAddsOnViewModel by navGraphViewModels(R.id.nav_booking)

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

        setOnBtnBackSelected()
    }

    private fun setOnBtnBackSelected() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onButtonSelected() {
        binding.btnSelect.setOnClickListener {
            onButtonSelectListener()
        }

        binding.safeBtnSelect.setOnClickListener {
            onButtonSelectListener(true)
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
            setPassengerToDisplay(
                preferableDeparture?.adultPassenger,
                preferableDeparture?.childPassenger,
                preferableDeparture?.infantPassenger
            )
        }

        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { departure ->
            val safeTotalPrice = departure.totalPrice + Insurance.PriceList.travelInsurance

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

            binding.priceTicket.text = getString(R.string.tv_price_ticket, departure.seatPrice)
            binding.totalPrice.text = getString(R.string.tv_total_price, departure.totalPrice)
            binding.point.text = getString(R.string.tv_point, departure.points)


            //safe section.
            binding.safePriceTicket.text = getString(R.string.tv_price_ticket, departure.seatPrice)
            binding.safeTotalPrice.text = getString(R.string.tv_total_price, safeTotalPrice)
            binding.safePoint.text = getString(R.string.tv_point, departure.points)

        }
    }

    private fun setPassengerToDisplay(
        initialAdultPassenger: Int?,
        initialChildPassenger: Int?,
        initialInfantPassenger: Int?
    ) {
        val adult = initialAdultPassenger ?: 0
        val child = initialChildPassenger ?: 0
        val infant = initialInfantPassenger ?: 0
        val tvAdult = binding.tvAdultPassenger
        val tvChild = binding.tvChildPassenger
        val tvInfant = binding.tvInfantPassenger

        if (adult > 0 && child > 0)
            binding.tvComaBetweenAdultChild.visibility = View.VISIBLE

        if (child > 0 && infant > 0)
            binding.tvComaBetweenChildInfant.visibility = View.VISIBLE

        if (adult > 0) {
            tvAdult.text = getString(R.string.tv_adult_passenger, adult)
            tvAdult.visibility = View.VISIBLE
        }
        if (child > 0) {
            tvChild.text = getString(R.string.tv_child_passenger, child)
            tvChild.visibility = View.VISIBLE
        }
        if (infant > 0) {
            tvInfant.text = getString(R.string.tv_infant_passenger, infant)
            tvInfant.visibility = View.VISIBLE
        }
    }


//    private fun getFlightBundle(tag: String): FlightModel? =
//        arguments?.getParcelable(tag)
//
//    private fun getFindTicketBundle(tag: String): FindTicketModel? =
//        arguments?.getParcelable(tag)

}