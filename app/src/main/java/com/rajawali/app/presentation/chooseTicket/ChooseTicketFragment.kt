package com.rajawali.app.presentation.chooseTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentChooseTicketBinding
import com.rajawali.app.presentation.booking.OneWayTripFragment
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.app.util.NavigationUtils.safeNavigateUsingID
import com.rajawali.core.domain.enums.AirportTypeEnum
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.presentation.adapter.TicketAdapter
import timber.log.Timber

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
        binding.tvDepartureScheduleLabel.text = getString(R.string.tv_choose_ticket_departure_schedule_label)

        //display chosen ticket
        setChosenTicketView()

        getPreferredTicket()
    }

    private fun setChosenTicketView() {
        viewModel.departureTicket.observe(viewLifecycleOwner) {ticket ->
            val departureTicket = binding.includeChosenDepartureTicket

            departureTicket.priceTicket.text = getString(com.rajawali.core.R.string.tv_price_ticket, ticket.seatPrice)
            departureTicket.airplaneId.text = ticket.airplane.airplaneCode
            departureTicket.point.text = getString(com.rajawali.core.R.string.tv_point, ticket.points)
            departureTicket.tvDepartureCityCodeLabel.text = ticket.sourceAirport.cityCode
            departureTicket.tvArriveCityCodeLabel.text = ticket.destinationAirport.cityCode
            departureTicket.tvDepartureTime.text = ticket.departureTime
            departureTicket.tvArriveTime.text = ticket.arrivalTime

            binding.tvDepartureScheduleLabel.text = getString(R.string.tv_choose_ticket_return_schedule_label)

        }
    }

    private fun getParcelableBundle(tag: String): FindTicketModel? =
        arguments?.getParcelable<FindTicketModel>(tag)

    private fun getBooleanBundle(tag: String): Boolean =
        arguments?.getBoolean(tag) ?: false

    private fun getPreferredTicket() {
        val isRoundTrip = getBooleanBundle(OneWayTripFragment.ROUND_TRIP)
        val departureTrip = getParcelableBundle(OneWayTripFragment.DEPARTURE)

        Timber.d("setChosenTicketView: $departureTrip")

        roundTrip.setRoundTrip(isRoundTrip)

        when (isRoundTrip) {
            true -> {
                val returnTrip = getParcelableBundle(OneWayTripFragment.RETURN)

                if (departureTrip != null) {
                    val tickets = getTickets(departureTrip)
                    displayTicketToUI(tickets, AirportTypeEnum.DEPARTURE)
                }
                if (returnTrip != null) {
                    val tickets = getTickets(returnTrip)
                    displayTicketToUI(tickets, AirportTypeEnum.ARRIVING)
                }

            }

            false -> {
                if (departureTrip != null) {
                    val tickets = getTickets(departureTrip)
                    displayTicketToUI(tickets, AirportTypeEnum.DEPARTURE)
                }
            }
        }
    }

    //TODO rename
    private fun displayTicketToUI(
        tickets: LiveData<UCResult<List<FlightModel>>>,
        flyingType: AirportTypeEnum
    ) {
        tickets.observe(viewLifecycleOwner) { result ->

            when (result) {
                is UCResult.Error -> {} //TODO()

                is UCResult.Success -> {
                    setDisplayAdapter(result.data, flyingType)
                }
            }
        }
    }

    private fun setDisplayAdapter(
        data: List<FlightModel>,
        flyingType: AirportTypeEnum
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

        _adapter.onClick(flyingType)
    }

    private fun TicketAdapter.onClick(flyingType: AirportTypeEnum) {
        roundTrip.isRoundTrip.observe(viewLifecycleOwner) { isRoundTrip ->
            when (isRoundTrip) {
                true -> {
                    when (flyingType) {
                        AirportTypeEnum.DEPARTURE -> {

                            this.setOnTicketClickCallback(object :
                                TicketAdapter.OnTicketClickCallback {
                                override fun onTicketClickCallback(ticket: FlightModel) {
                                    viewModel.setDepartureTicket(ticket)
                                }

                                override fun onRescheduleOptionsClickCallback(ticket: FlightModel) {
                                    onDropdownButtonClicked(ticket)
                                }

                            })
                        }

                        AirportTypeEnum.ARRIVING -> {
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
        preferred: FindTicketModel
    ): LiveData<UCResult<List<FlightModel>>> {

        return viewModel.getPreferredTickets(
            departure = preferred.departureCityCode,
            destination = preferred.destinationCityCode,
            adultPassenger = preferred.adultPassenger,
            childPassenger = preferred.childPassenger,
            infantPassenger = preferred.infantPassenger,
            departureDate = preferred.departureDate,
            seatType = preferred.seatType.name.uppercase(),
        )

    }

    companion object {
        const val DEPARTURE = "ticketDeparture"
        const val RETURN = "ticketReturn"
    }

}