package com.rajawali.app.presentation.chooseTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentChooseTicketBinding
import com.rajawali.app.presentation.booking.OneWayTripFragment
import com.rajawali.app.util.AppUtils.getPassengerClassText
import com.rajawali.app.util.AppUtils.isPassengerExist
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.app.util.NavigationUtils.safeNavigateUsingID
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.presentation.adapter.DateAdapter
import com.rajawali.core.presentation.adapter.TicketAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.time.LocalDate

class ChooseTicketFragment : Fragment() {
    private var _binding: FragmentChooseTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChooseTicketViewModel by viewModel()
    private val roundTrip: IsRoundTripViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        //default label
        binding.tvDepartureScheduleLabel.text =
            getString(R.string.tv_choose_ticket_departure_schedule_label)

        //display chosen ticket
        setChosenTicketView()

        getPreferredTicket()

        displayPreferredTicketOnToolbar()

        displayDates()

        onBackButtonClicked()
    }

    private fun onBackButtonClicked() {
        binding.includeToolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayDates() {
        val rvDates = binding.rvPickDate
        val snapHelper = PagerSnapHelper()
        val _adapter = DateAdapter()
        val date = LocalDate.parse(getParcelableBundle(OneWayTripFragment.DEPARTURE)?.departureDate)

        val _layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvDates.layoutManager = _layoutManager
//        sticky when moving the recyclerview
        snapHelper.attachToRecyclerView(rvDates)

        viewModel.getDates(date).observe(viewLifecycleOwner) {
            when (it) {
                is UCResult.Error ->
                    Toast.makeText(activity, "date is error", Toast.LENGTH_SHORT).show()

                is UCResult.Success -> {
                    _adapter.submitList(it.data)

                    rvDates.apply {
                        adapter = _adapter
                        setHasFixedSize(true)
                    }

                    rvDates.layoutManager?.scrollToPosition(4)
                }
            }
        }

        _adapter.setOnDateClick()
    }

    private fun DateAdapter.setOnDateClick() {
        this.setOnTicketClickCallback(object : DateAdapter.OnDateClickCallback {
            override fun onDateClickCallback(date: LocalDate) {
                val rvDates = binding.rvPickDate
                val _adapter = DateAdapter()
                viewModel.getDates(date).observe(viewLifecycleOwner) {
                    when (it) {
                        is UCResult.Error ->
                            Toast.makeText(activity, "date is error", Toast.LENGTH_SHORT).show()

                        is UCResult.Success -> {
                            _adapter.submitList(it.data)
                            rvDates.adapter = _adapter

                            val layoutManager = rvDates.layoutManager as LayoutManager
                            layoutManager.scrollToPosition((_adapter.itemCount / 2) - 1)

                        }

                    }
                }
                _adapter.setOnDateClick()

                getPreferredTicket(date)
            }
        })
    }

    private fun displayPreferredTicketOnToolbar() {
        val toolbar = binding.includeToolbar
        val preferred = getParcelableBundle(OneWayTripFragment.DEPARTURE)
        toolbar.tvRoute.text = getString(
            R.string.tv_choose_ticket_flight_route,
            preferred?.departureCity,
            preferred?.destinationCity
        )

        val passenger = setPassengerToDisplay(
            preferred?.adultPassenger,
            preferred?.childPassenger,
            preferred?.infantPassenger
        )

        val seatType = activity.getPassengerClassText(preferred?.seatType)

        toolbar.tvRouteDescription.text =
            getString(
                R.string.tv_choose_ticket_flight_route_description,
                preferred?.departureDate,
                passenger,
                seatType
            )
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

    private fun setChosenTicketView() {
        viewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
            val departureTicket = binding.includeChosenDepartureTicket

            departureTicket.root.visibility = View.VISIBLE
            departureTicket.priceTicket.text =
                getString(com.rajawali.core.R.string.tv_price_ticket, ticket.seatPrice)
            departureTicket.airplaneId.text = ticket.airplane.airplaneCode
            departureTicket.point.text =
                getString(com.rajawali.core.R.string.tv_point, ticket.points)
            departureTicket.tvDepartureCityCodeLabel.text = ticket.sourceAirport.cityCode
            departureTicket.tvArriveCityCodeLabel.text = ticket.destinationAirport.cityCode
            departureTicket.tvDepartureTime.text = ticket.departureTime
            departureTicket.tvArriveTime.text = ticket.arrivalTime

            binding.tvDepartureScheduleLabel.text =
                getString(R.string.tv_choose_ticket_return_schedule_label)

        }
    }

    private fun getParcelableBundle(tag: String): FindTicketModel? =
        arguments?.getParcelable(tag)

    private fun getBooleanBundle(tag: String): Boolean =
        arguments?.getBoolean(tag) ?: false

    private fun getPreferredTicket(customDate: LocalDate? = null) {
        val isRoundTrip = getBooleanBundle(OneWayTripFragment.ROUND_TRIP)
        val departureTrip = getParcelableBundle(OneWayTripFragment.DEPARTURE)


        roundTrip.setRoundTrip(isRoundTrip)

        when (isRoundTrip) {
            true -> {

                viewModel.isDeparturePicked.observe(viewLifecycleOwner) { isDeparturePicked ->

                    when (isDeparturePicked) {
                        true -> {
                            val returnTrip = getParcelableBundle(OneWayTripFragment.RETURN)

                            if (returnTrip != null) {
//                                val tickets = getTickets(returnTrip, customDate)
//                                Timber.d("getPreferredTicket: $isDeparturePicked : $tickets")
//
//                                displayTicketToUI(tickets)
                                customDate.isPreferredCustomDate(returnTrip)
                            }

                        }

                        false -> {

                            if (departureTrip != null) {

//                                val tickets = getTickets(departureTrip, customDate)
//                                displayTicketToUI(tickets)
                                customDate.isPreferredCustomDate(departureTrip)
                            }

                        }
                    }
                }

            }

            false -> {
                if (departureTrip != null) {
//                    val tickets = getTickets(departureTrip, customDate)
//                    displayTicketToUI(tickets)
                    customDate.isPreferredCustomDate(departureTrip)

                }
            }
        }
    }

    private fun LocalDate?.isPreferredCustomDate(
        data: FindTicketModel,
    ) {
        when (this != null) {
            true -> {
                val tickets = getTickets(data, this)
                displayTicketToUI(tickets)
            }
            false -> {
                val tickets = getTickets(data)
                displayTicketToUI(tickets)
            }
        }

    }

    //TODO rename
    private fun displayTicketToUI(
        tickets: LiveData<UCResult<List<FlightModel>>>,
    ) {
        tickets.observe(viewLifecycleOwner) { result ->

            when (result) {
                is UCResult.Error -> {
                    binding.tvNoTickets.visibility = View.VISIBLE
                    binding.rvTicketList.visibility = View.GONE

                }

                is UCResult.Success -> {
                    Timber.d("displayTicketToUI: ${result.data}")

                    binding.tvNoTickets.visibility = View.GONE
                    binding.rvTicketList.visibility = View.VISIBLE

                    setDisplayAdapter(result.data)
                }
            }
        }
    }

    private fun setDisplayAdapter(
        data: List<FlightModel>,
    ) {
        val rvPromotion = binding.rvTicketList
        val _adapter = TicketAdapter()
        val _layoutManager =
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        Timber.d("setDisplayAdapter : $data")

        _adapter.submitList(data)
        rvPromotion.apply {
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }

        _adapter.onClick()
    }

    private fun TicketAdapter.onClick() {
        roundTrip.isRoundTrip.observe(viewLifecycleOwner) { isRoundTrip ->
            when (isRoundTrip) {
                true -> {
                    viewModel.isDeparturePicked.observe(viewLifecycleOwner) { isDeparturePicked ->

                        when (isDeparturePicked) {
                            false -> {

                                this.setOnTicketClickCallback(object :
                                    TicketAdapter.OnTicketClickCallback {
                                    override fun onTicketClickCallback(ticket: FlightModel) {

                                        /*
                                        visible gone forever if the return tickets is none.
                                        visibility will be change on displayTicketToUI() when return ticket is not null
                                         */
                                        binding.tvNoTickets.visibility = View.GONE


                                        viewModel.setDepartureTicket(ticket)
                                        viewModel.setDeparturePicked()

                                    }

                                    override fun onRescheduleOptionsClickCallback(ticket: FlightModel) {
                                        onDropdownButtonClicked(ticket)
                                    }

                                })
                            }

                            true -> {
                                this.setOnTicketClickCallback(object :
                                    TicketAdapter.OnTicketClickCallback {
                                    override fun onTicketClickCallback(ticket: FlightModel) {
                                        viewModel.departureTicket.observe(viewLifecycleOwner) { departure ->
                                            val bundle = Bundle()
                                            val destination =
                                                R.id.action_chooseTicketFragment_to_selectedTicketFragment

                                            bundle.putParcelable(DEPARTURE, departure)
                                            bundle.putParcelable(RETURN, ticket)

                                            findNavController().safeNavigateUsingID(
                                                destination,
                                                bundle
                                            )
                                        }
                                    }

                                    override fun onRescheduleOptionsClickCallback(ticket: FlightModel) {
                                        onDropdownButtonClicked(ticket)
                                    }

                                })
                            }
                        }
                    }

                }

                false ->
                    this.setOnTicketClickCallback(object : TicketAdapter.OnTicketClickCallback {
                        override fun onTicketClickCallback(ticket: FlightModel) {
                            val bundle = Bundle()
                            val destination =
                                R.id.action_chooseTicketFragment_to_selectedTicketFragment

                            bundle.putParcelable(DEPARTURE, ticket)

                            findNavController().safeNavigateUsingID(
                                destination,
                                bundle
                            )
                        }

                        override fun onRescheduleOptionsClickCallback(ticket: FlightModel) {
                            onDropdownButtonClicked(ticket)
                        }

                    })
            }
        }
    }

    private fun onDropdownButtonClicked(ticket: FlightModel) {
        val destination =
            ChooseTicketFragmentDirections.actionChooseTicketFragmentToFlightDetailBottomSheetDialog(
                ticket
            )
        findNavController().safeNavigate(destination)
    }

    private fun getTickets(
        preferred: FindTicketModel,
        customDate: LocalDate? = null
    ): LiveData<UCResult<List<FlightModel>>> {
        return viewModel.getPreferredTickets(
            departure = preferred.departureId,
            destination = preferred.destinationId,
            adultPassenger = preferred.adultPassenger,
            childPassenger = preferred.childPassenger,
            infantPassenger = preferred.infantPassenger,
            departureDate = when (customDate != null) {
                true ->
                    customDate.toString()

                false ->
                    preferred.departureDate
            },
            seatType = preferred.seatType.name.uppercase(),
        )

    }

    companion object {
        const val DEPARTURE = "ticketDeparture"
        const val RETURN = "ticketReturn"
    }

}