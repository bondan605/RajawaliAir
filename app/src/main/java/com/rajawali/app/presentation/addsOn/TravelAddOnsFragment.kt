package com.rajawali.app.presentation.addsOn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentTravelAddOnsBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.model.Insurance
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel

class TravelAddOnsFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentTravelAddOnsBinding? = null

    private val ticketViewModel: TicketViewModel by activityViewModels()
    private val addsOnViewModel: TravelAddsOnViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTravelAddOnsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do something here.

        onInsurancesClicked()

        populateTravelInsuranceView()
        populateBaggageInsuranceView()
        populateFlightDelayInsuranceView()

        setLoyaltyPointAmount()
        setTotalPrice()

        setOnAddBaggageClicked()
        setOnAddMealsClicked()

        onBaggageIcon()
        onMealsIcon()

        setDropdownValue()
        setDropdownButton()
        onDropdownIcon()
        onDropdownContent()
    }

    private fun setDropdownButton() {
        binding.ivDropdownBtn.setOnClickListener {
            addsOnViewModel.setDropdownState()
        }
    }

    private fun onDropdownContent() {
        addsOnViewModel.dropdownBtnState.observe(viewLifecycleOwner) { state ->
            when (state) {
                true ->
                    binding.includePriceDetails.root.visibility = View.VISIBLE

                false ->
                    binding.includePriceDetails.root.visibility = View.GONE
            }
        }
    }

    private fun onDropdownIcon() {
        addsOnViewModel.dropdownBtnState.observe(viewLifecycleOwner) { state ->
            val icon =
                when (state) {
                    true ->
                        R.drawable.arrow_up_20

                    false ->
                        R.drawable.arrow_down_20
                }

            binding.ivDropdownBtn.setImageResource(icon)
        }
    }

    private fun setDropdownValue() {
        val include = binding.includePriceDetails

        //flight route section
        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { passenger ->
            include.tvDepartureLocationLabel.text = passenger.sourceAirport.city
            include.tvArriveLocationLabel.text = passenger.destinationAirport.city
        }

        //add-ons section
        addsOnViewModel.totalItem.observe(viewLifecycleOwner) { item ->
            val baggage = item.getValue(BAGGAGE)
            val meals = item.getValue(MEALS)
            val travelInsurance = item.getValue(TRAVEL_INSURANCE)
            val baggageInsurance = item.getValue(BAGGAGE_INSURANCE)
            val flightDelayInsurance = item.getValue(FLIGHT_DELAY_INSURANCE)

            include.tvBaggageLabel.viewVisibility(baggage)
            include.tvTotalBaggagePrice.viewVisibility(baggage)

            include.tvMealsLabel.viewVisibility(meals)
            include.tvTotalMealsPrice.viewVisibility(meals)

            include.tvTravelInsuranceLabel.viewVisibility(travelInsurance)
            include.tvTravelInsurancePrice.viewVisibility(travelInsurance)

            include.tvBaggageInsuranceLabel.viewVisibility(baggageInsurance)
            include.tvBaggageInsurancePrice.viewVisibility(baggageInsurance)

            include.tvFlightDelayInsuranceLabel.viewVisibility(flightDelayInsurance)
            include.tvFlightDelayInsurancePrice.viewVisibility(flightDelayInsurance)


            include.tvTotalBaggagePrice.text =
                getString(R.string.tv_total_price, baggage)

            include.tvTotalMealsPrice.text =
                getString(R.string.tv_total_price, meals)

            include.tvTravelInsurancePrice.text =
                getString(R.string.tv_total_price, travelInsurance)

            include.tvBaggageInsurancePrice.text =
                getString(R.string.tv_total_price, baggageInsurance)

            include.tvFlightDelayInsurancePrice.text =
                getString(R.string.tv_total_price, flightDelayInsurance)
        }

        //add-ons section
        ticketViewModel.preferableReturn.observe(viewLifecycleOwner) { passenger ->
            val totalPassenger =
                passenger.adultPassenger + passenger.childPassenger + passenger.infantPassenger

            include.tvTravelInsuranceLabel.text =
                getString(R.string.tv_travel_insurance_amount, totalPassenger)

            include.tvBaggageInsuranceLabel.text =
                getString(R.string.tv_baggage_insurance_amount, totalPassenger)

            include.tvFlightDelayInsuranceLabel.text =
                getString(R.string.tv_flight_delay_insurance_amount, totalPassenger)
        }

        //passenger section
        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { passenger ->
            //visibility
            include.tvAdultPassengerCount.viewVisibility(passenger.adultPassenger)
            include.tvChildPassengerCount.viewVisibility(passenger.childPassenger)
            include.tvInfantPassengerCount.viewVisibility(passenger.infantPassenger)

            //set up the amount
            include.tvAdultPassengerCount.text =
                getString(R.string.tv_adult_amount, passenger.adultPassenger)
            include.tvChildPassengerCount.text =
                getString(R.string.tv_child_amount, passenger.childPassenger)
            include.tvInfantPassengerCount.text =
                getString(R.string.tv_infant_amount, passenger.infantPassenger)

            ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
                //visibility
                include.tvAdultPassengerTotalPrice.viewVisibility(passenger.adultPassenger)
                include.tvChildPassengerTotalPrice.viewVisibility(passenger.childPassenger)
                include.tvInfantPassengerTotalPrice.viewVisibility(passenger.infantPassenger)

                //set up the price
                val totalAdultPrice : Int = ticket.seatPrice * passenger.adultPassenger
                val totalChildPrice : Int = (ticket.seatPrice * passenger.childPassenger - (ticket.seatPrice * 0.10)).toInt()
                val totalInfantPrice : Int = (ticket.seatPrice * passenger.infantPassenger - (ticket.seatPrice * 0.20)).toInt()

                include.tvAdultPassengerTotalPrice.text =
                    getString(R.string.tv_total_price, totalAdultPrice)
                include.tvChildPassengerTotalPrice.text =
                    getString(R.string.tv_total_price, totalChildPrice)
                include.tvInfantPassengerTotalPrice.text =
                    getString(R.string.tv_total_price, totalInfantPrice)
            }
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

    private fun setLoyaltyPointAmount() {
        ticketViewModel.departureTicket.observe(viewLifecycleOwner) {
            binding.loyaltyPoint.text = getString(R.string.tv_point_gain, it.points)
        }
    }

    private fun setOnAddBaggageClicked() {
        binding.llAddBaggage.setOnClickListener {
            val destination = TravelAddOnsFragmentDirections
                .actionTravelAddOnsFragmentToBaggageFragment()

            findNavController().safeNavigate(destination)
        }
    }

    private fun setOnAddMealsClicked() {
        binding.cardAddFood.setOnClickListener {
            val destination = TravelAddOnsFragmentDirections
                .actionTravelAddOnsFragmentToMealsFragment()

            findNavController().safeNavigate(destination)
        }
    }

    //put the value into a map with a key. then sum the value and use it into TextView.text
    private fun setTotalPrice() {
        var totalPassenger = 1

        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { passenger ->
            totalPassenger = passenger.adultPassenger + passenger.childPassenger + passenger.infantPassenger
        }

        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
            addsOnViewModel.setTotalItem(PASSENGER, ticket.totalPrice)
        }

        addsOnViewModel.totalMealsPrice.observe(viewLifecycleOwner) { totalPrice ->
            addsOnViewModel.setTotalItem(MEALS, totalPrice)
        }

        addsOnViewModel.totalBaggagePrice.observe(viewLifecycleOwner) { totalPrice ->
            addsOnViewModel.setTotalItem(BAGGAGE, totalPrice)
        }

        addsOnViewModel.travelInsurance.observe(viewLifecycleOwner) { isInsurance ->
            when (isInsurance) {
                true ->
                    addsOnViewModel.setTotalItem(
                        TRAVEL_INSURANCE,
                        Insurance.PriceList.travelInsurance * totalPassenger
                    )

                false ->
                    addsOnViewModel.setTotalItem(TRAVEL_INSURANCE, 0)
            }
        }

        addsOnViewModel.baggageInsurance.observe(viewLifecycleOwner) { isInsurance ->
            when (isInsurance) {
                true ->
                    addsOnViewModel.setTotalItem(
                        BAGGAGE_INSURANCE,
                        Insurance.PriceList.baggageInsurance * totalPassenger
                    )

                false ->
                    addsOnViewModel.setTotalItem(BAGGAGE_INSURANCE, 0)
            }
        }

        addsOnViewModel.flightDelayInsurance.observe(viewLifecycleOwner) { isInsurance ->
            when (isInsurance) {
                true ->
                    addsOnViewModel.setTotalItem(
                        FLIGHT_DELAY_INSURANCE,
                        Insurance.PriceList.flightDelayInsurance * totalPassenger
                    )

                false ->
                    addsOnViewModel.setTotalItem(FLIGHT_DELAY_INSURANCE, 0)
            }
        }

        addsOnViewModel.totalItem.observe(viewLifecycleOwner) { items ->
            addsOnViewModel.setTotalPrice(items)
        }

        addsOnViewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.totalPrice.text = getString(R.string.tv_total_price, totalPrice)

            //dropdown
            binding.includePriceDetails.tvTotalPriceValue.text =
                getString(R.string.tv_total_price, totalPrice)
        }
    }

    private fun populateFlightDelayInsuranceView() {
        addsOnViewModel.flightDelayInsurance.observe(viewLifecycleOwner) { isInsurance ->
            binding.checkboxDelay.setCheckBoxState(isInsurance)
        }
    }

    private fun populateBaggageInsuranceView() {
        addsOnViewModel.baggageInsurance.observe(viewLifecycleOwner) { isInsurance ->
            binding.checkboxBaggage.setCheckBoxState(isInsurance)
        }
    }

    private fun populateTravelInsuranceView() {
        addsOnViewModel.travelInsurance.observe(viewLifecycleOwner) { isInsurance ->
            binding.checkboxTravel.setCheckBoxState(isInsurance)
        }
    }

    private fun CheckBox.setCheckBoxState(value: Boolean) {
        this.isChecked = value
    }


    //this is used to save user choice
    private fun onInsurancesClicked() {
        binding.checkboxTravel.setOnClickListener {
            addsOnViewModel.setTravelInsurance()
        }

        binding.checkboxBaggage.setOnClickListener {
            addsOnViewModel.setBaggageInsurance()
        }

        binding.checkboxDelay.setOnClickListener {
            addsOnViewModel.setDelayInsurance()
        }

    }

    private fun onMealsIcon() {
        addsOnViewModel.totalMealsPrice.observe(viewLifecycleOwner) {
            val isExist = it > 0

            binding.tvMealsDescription.setFlightFacilitiesDescriptionBackground(isExist)
            binding.btnAddFood.setFlightFacilitiesCheckIcon(isExist)
        }
    }

    private fun onSeatsIcon() {
        addsOnViewModel.seatNumber.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.tvSeatsDescription.setFlightFacilitiesDescriptionBackground(it)
                    binding.btnAddSeatNumber.setFlightFacilitiesCheckIcon(it)
                }

                false -> {
                    binding.tvSeatsDescription.setFlightFacilitiesDescriptionBackground(it)
                    binding.btnAddSeatNumber.setFlightFacilitiesCheckIcon(it)
                }

            }
        }

    }

    private fun onBaggageIcon() {
        addsOnViewModel.totalBaggagePrice.observe(viewLifecycleOwner) {
            val isExist = it > 0

            binding.tvBaggageDescription.setFlightFacilitiesDescriptionBackground(isExist)
            binding.btnAddBaggage.setFlightFacilitiesCheckIcon(isExist)
        }
    }

    private fun TextView.setFlightFacilitiesDescriptionBackground(status: Boolean) {
        val color =
            when (status) {
                true -> {
                    resources.getColor(R.color.green)
                }

                false ->
                    resources.getColor(R.color.grey_text)
            }

        this.setTextColor(color)
    }

    private fun ImageView.setFlightFacilitiesCheckIcon(status: Boolean) {
        val icon = when (status) {
            true ->
                R.drawable.ic_check_fill

            false ->
                R.drawable.add_circle
        }

        this.setImageResource(icon)
    }

    companion object {
        const val PASSENGER = "passenger"
        const val BAGGAGE = "baggage"
        const val MEALS = "meals"
        const val TRAVEL_INSURANCE = "travelInsurance"
        const val BAGGAGE_INSURANCE = "baggageInsurance"
        const val FLIGHT_DELAY_INSURANCE = "flightDelayInsurance"
    }
}