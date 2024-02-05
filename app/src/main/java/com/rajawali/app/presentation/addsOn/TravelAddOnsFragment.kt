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
import com.rajawali.core.domain.enums.AddsOnEnum
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel
import timber.log.Timber

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

//        onBaggageClicked()
//        onSeatsClicked()
//        onMealsClicked()

        onInsurancesClicked()

        populateTravelInsuranceView()
        populateBaggageInsuranceView()
        populateFlightDelayInsuranceView()

        setTotalPrice()

        setOnAddBaggageClicked()
        setOnAddMealsClicked()

        onBaggageClicked()
    }

    //TODO Instead of set (viewModel.setSeat, etc) the the seat, baggage, and meal on clicked. Do the set seat, baggage, and meal when save button is clicked.

    private fun setOnAddBaggageClicked() {
        binding.llAddBaggage.setOnClickListener {
            Timber.d("setOnAddBaggageClicked: clicked")
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

    private fun setTotalPrice() {

        addsOnViewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            binding.totalPrice.text = getString(R.string.tv_total_price, price)
        }
    }

    private fun populateFlightDelayInsuranceView() {
        addsOnViewModel.flightDelayInsurance.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.checkboxDelay.setCheckBoxState(it)
                    addsOnViewModel.setTotalPrice(AddsOnEnum.DELAY_INSURANCE, checkBox = true)
                }

                false -> {
                    binding.checkboxDelay.setCheckBoxState(it)
                    addsOnViewModel.setTotalPrice(AddsOnEnum.DELAY_INSURANCE, checkBox = false)
                }
            }
        }
    }

    private fun populateBaggageInsuranceView() {
        addsOnViewModel.baggageInsurance.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.checkboxBaggage.setCheckBoxState(it)
                    addsOnViewModel.setTotalPrice(AddsOnEnum.BAGGAGE_INSURANCE, checkBox = true)
                }

                false -> {
                    binding.checkboxBaggage.setCheckBoxState(it)
                    addsOnViewModel.setTotalPrice(AddsOnEnum.BAGGAGE_INSURANCE, checkBox = false)
                }
            }
        }
    }

    private fun populateTravelInsuranceView() {
        addsOnViewModel.travelInsurance.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.checkboxTravel.setCheckBoxState(it)
                    addsOnViewModel.setTotalPrice(AddsOnEnum.TRAVEL_INSURANCE, checkBox = true)
                }

                false -> {
                    binding.checkboxTravel.setCheckBoxState(it)
                    addsOnViewModel.setTotalPrice(AddsOnEnum.TRAVEL_INSURANCE, checkBox = false)
                }
            }
        }
    }

    private fun CheckBox.setCheckBoxState(value: Boolean) {
        when (value) {
            true ->
                this.isChecked = true

            false ->
                this.isChecked = false
        }

    }

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

    private fun onMealsClicked() {
        binding.btnAddSeatNumber.setOnClickListener {
            addsOnViewModel.seatNumber.observe(viewLifecycleOwner) {
                when (it) {
                    true -> {
                        binding.tvMealsDescription.setFlightFacilitiesDescriptionBackground(it)
                        binding.btnAddFood.setFlightFacilitiesCheckIcon(it)
                    }

                    false -> {
                        binding.tvMealsDescription.setFlightFacilitiesDescriptionBackground(it)
                        binding.btnAddFood.setFlightFacilitiesCheckIcon(it)
                    }

                }
            }
        }

    }

    private fun onSeatsClicked() {
        binding.btnAddSeatNumber.setOnClickListener {
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

    }

    private fun onBaggageClicked() {
        binding.btnAddBaggage.setOnClickListener {
            addsOnViewModel.totalBaggagePrice.observe(viewLifecycleOwner) {
                when (it > 0) {
                    true -> {
                        binding.tvBaggageDescription.setFlightFacilitiesDescriptionBackground(true)
                        binding.btnAddBaggage.setFlightFacilitiesCheckIcon(true)
                    }

                    false -> {
                        binding.tvBaggageDescription.setFlightFacilitiesDescriptionBackground(false)
                        binding.btnAddBaggage.setFlightFacilitiesCheckIcon(false)
                    }

                }
            }
        }
    }

    private fun TextView.setFlightFacilitiesDescriptionBackground(status: Boolean) {
        when (status) {
            true -> {
                this.setTextColor(resources.getColor(R.color.green))
            }

            false ->
                this.setTextColor(resources.getColor(R.color.grey_text))
        }
    }

    private fun ImageView.setFlightFacilitiesCheckIcon(status: Boolean) {
        when (status) {
            true ->
                this.setImageResource(R.drawable.add_circle)

            false ->
                this.setImageResource(R.drawable.ic_check_fill)
        }

    }
}