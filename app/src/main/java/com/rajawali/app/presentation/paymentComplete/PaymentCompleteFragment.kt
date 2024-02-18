package com.rajawali.app.presentation.paymentComplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rajawali.app.NavBookingDirections
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentPaymentCompleteBinding
import com.rajawali.app.presentation.addsOn.TravelAddOnsFragment
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel
import com.rajawali.core.util.DateFormat

class PaymentCompleteFragment : Fragment() {
    private var _binding: FragmentPaymentCompleteBinding? = null
    private val binding get() = _binding!!

    //    private val ticketViewModel: TicketViewModel by activityViewModels()
//    private val addsOnViewModel: TravelAddsOnViewModel by activityViewModels()
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val addsOnViewModel: TravelAddsOnViewModel by navGraphViewModels(R.id.nav_booking)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentCompleteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here
        setPaymentStepsView()

        populatePaymentInformationCard()
        populatePriceDetails()
        populateOrderId()


        setOnBtnBackClicked()
        setOnBtnDoneClicked()
    }

    private fun populatePaymentInformationCard() {
        ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
            val purchaseDate = DateFormat.formatDateStringToAbbreviatedString(payment.createdAt)
            val method =
                payment.method.replace("_", " ", ignoreCase = true).lowercase().capitalize()

            binding.tvPurchaseDateValue.text = purchaseDate
            binding.tvPaymentMethodValue.text = method
            binding.tvTotalPriceValue.text =
                getString(R.string.tv_total_price, payment.reservation.totalPrice)
        }
    }

    private fun TextView.viewVisibility(value: Int) {
        val visibility =
            when (value > 0) {
                true ->
                    View.VISIBLE

                false ->
                    View.GONE
            }

        this.visibility = visibility
    }

    private fun populatePriceDetails() {

        val included = binding.includePriceDetails

        //flight route section
        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { passenger ->
            included.tvDepartureLocationLabel.text = passenger.sourceAirport.city
            included.tvArriveLocationLabel.text = passenger.destinationAirport.city
        }

        //add-ons section
        addsOnViewModel.totalItem.observe(viewLifecycleOwner) { item ->
            val baggage = item.getValue(TravelAddOnsFragment.BAGGAGE)
            val meals = item.getValue(TravelAddOnsFragment.MEALS)
            val travelInsurance = item.getValue(TravelAddOnsFragment.TRAVEL_INSURANCE)
            val baggageInsurance = item.getValue(TravelAddOnsFragment.BAGGAGE_INSURANCE)
            val flightDelayInsurance = item.getValue(TravelAddOnsFragment.FLIGHT_DELAY_INSURANCE)

            included.tvBaggageLabel.viewVisibility(baggage)
            included.tvTotalBaggagePrice.viewVisibility(baggage)

            included.tvMealsLabel.viewVisibility(meals)
            included.tvTotalMealsPrice.viewVisibility(meals)

            included.tvTravelInsuranceLabel.viewVisibility(travelInsurance)
            included.tvTravelInsurancePrice.viewVisibility(travelInsurance)

            included.tvBaggageInsuranceLabel.viewVisibility(baggageInsurance)
            included.tvBaggageInsurancePrice.viewVisibility(baggageInsurance)

            included.tvFlightDelayInsuranceLabel.viewVisibility(flightDelayInsurance)
            included.tvFlightDelayInsurancePrice.viewVisibility(flightDelayInsurance)


            included.tvTotalBaggagePrice.text =
                getString(R.string.tv_total_price, baggage)

            included.tvTotalMealsPrice.text =
                getString(R.string.tv_total_price, meals)

            included.tvTravelInsurancePrice.text =
                getString(R.string.tv_total_price, travelInsurance)

            included.tvBaggageInsurancePrice.text =
                getString(R.string.tv_total_price, baggageInsurance)

            included.tvFlightDelayInsurancePrice.text =
                getString(R.string.tv_total_price, flightDelayInsurance)
        }

        //add-ons section
        ticketViewModel.preferableReturn.observe(viewLifecycleOwner) { passenger ->
            val totalPassenger =
                passenger.adultPassenger + passenger.childPassenger + passenger.infantPassenger

            included.tvTravelInsuranceLabel.text =
                getString(R.string.tv_travel_insurance_amount, totalPassenger)

            included.tvBaggageInsuranceLabel.text =
                getString(R.string.tv_baggage_insurance_amount, totalPassenger)

            included.tvFlightDelayInsuranceLabel.text =
                getString(R.string.tv_flight_delay_insurance_amount, totalPassenger)
        }

        //passenger section
        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { passenger ->
            //visibility
            included.tvAdultPassengerCount.viewVisibility(passenger.adultPassenger)
            included.tvChildPassengerCount.viewVisibility(passenger.childPassenger)
            included.tvInfantPassengerCount.viewVisibility(passenger.infantPassenger)

            //set up the amount
            included.tvAdultPassengerCount.text =
                getString(R.string.tv_adult_amount, passenger.adultPassenger)
            included.tvChildPassengerCount.text =
                getString(R.string.tv_child_amount, passenger.childPassenger)
            included.tvInfantPassengerCount.text =
                getString(R.string.tv_infant_amount, passenger.infantPassenger)

            ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
                //visibility
                included.tvAdultPassengerTotalPrice.viewVisibility(passenger.adultPassenger)
                included.tvChildPassengerTotalPrice.viewVisibility(passenger.childPassenger)
                included.tvInfantPassengerTotalPrice.viewVisibility(passenger.infantPassenger)

                //set up the price
                val totalAdultPrice: Int = ticket.seatPrice * passenger.adultPassenger
                val totalChildPrice: Int =
                    (ticket.seatPrice * passenger.childPassenger - (ticket.seatPrice * 0.10)).toInt()
                val totalInfantPrice: Int =
                    (ticket.seatPrice * passenger.infantPassenger - (ticket.seatPrice * 0.20)).toInt()

                included.tvAdultPassengerTotalPrice.text =
                    getString(R.string.tv_total_price, totalAdultPrice)
                included.tvChildPassengerTotalPrice.text =
                    getString(R.string.tv_total_price, totalChildPrice)
                included.tvInfantPassengerTotalPrice.text =
                    getString(R.string.tv_total_price, totalInfantPrice)
            }
        }

    }

    private fun setPaymentStepsView() {
        val included = binding.includePaymentSteps

        included.ivStepTwo.setImageResource(R.drawable.ic_number_two_circle_filled)

        included.ivStepThree.setImageResource(R.drawable.ic_number_three_circle_filled)
    }

    private fun populateOrderId() {
        ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
            val text = getString(R.string.tv_order_id_value, payment.reservation.id)

            binding.includeAppbar.tvOrderId.text = text
            binding.tvOrderIdValue.text = text
        }
    }

    private fun setOnBtnBackClicked() {
        binding.includeAppbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnBtnDoneClicked() {
        binding.btnAlreadyPaid.setOnClickListener {
            val destination =
//                PaymentCompleteFragmentDirections.actionPaymentCompleteFragmentToHomePageFragment()
                NavBookingDirections.actionGlobalHomePageFragment()

            findNavController().safeNavigate(destination)
        }
    }
}