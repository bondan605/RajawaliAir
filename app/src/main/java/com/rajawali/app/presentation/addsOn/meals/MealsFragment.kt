package com.rajawali.app.presentation.addsOn.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentMealsBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.presentation.detailsInformation.DetailsInformationViewModel
import com.rajawali.core.domain.model.MealModel
import com.rajawali.core.domain.model.PassengerInputModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.presentation.adapter.MealsAdapter
import com.rajawali.core.presentation.adapter.PassengerSelectionAdapter
import com.rajawali.core.presentation.viewModel.MealsViewModel
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MealsFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentMealsBinding? = null

//    private val addsOnViewModel: TravelAddsOnViewModel by activityViewModels()
//    private val ticketViewModel: TicketViewModel by activityViewModels()
    private val addsOnViewModel: TravelAddsOnViewModel by navGraphViewModels(R.id.nav_booking)
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val mealsViewModel: MealsViewModel by viewModel()
    private val passenger: DetailsInformationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do something here.

        getPassenger()

        getMeals()

        setTotalPrice()

        dismiss()
    }

    private fun dismiss() {
        binding.btnSave.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setTotalPrice() {
        addsOnViewModel.passengerMealsList.observe(viewLifecycleOwner) { passengerMeals ->
            addsOnViewModel.setTotalMealsPrice(passengerMeals)
        }

        addsOnViewModel.totalMealsPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.totalPrice.text = getString(com.rajawali.common_resource.R.string.tv_total_price, totalPrice)
        }
    }

    private fun getPassenger() {
        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { preferableTrip ->
            passenger.createPassengerInput(
                adult = preferableTrip.adultPassenger,
                child = preferableTrip.childPassenger,
                infant = preferableTrip.infantPassenger
            ).observe(viewLifecycleOwner) { passenger ->
                Timber.d("getPassenger : $passenger")

                when (passenger) {
                    is CommonResult.Error -> TODO()
                    is CommonResult.Success -> {
                        val recyclerView = binding.rvPassengerSelection
                        val _adapter = PassengerSelectionAdapter()
                        val _layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )


                        _adapter.apply {
                            submitList(passenger.data)
                        }

                        recyclerView.apply {
                            adapter = _adapter
                            layoutManager = _layoutManager
                            setHasFixedSize(true)
                        }

                        onPassengerClicked(_adapter)
                    }
                }
            }
        }
    }

    private fun onPassengerClicked(adapter: PassengerSelectionAdapter) {
        adapter.setOnPassengerClickCallback(object :
            PassengerSelectionAdapter.OnPassengerClickCallback {
            override fun onPassengerClickCallback(passenger: PassengerInputModel) {
                mealsViewModel.setCurrentPassenger(passenger.id)
            }

            override fun setBackgroundColor(
                passenger: PassengerInputModel,
                cardPassenger: MaterialCardView,
                tvOrder: TextView,
                tvPassenger: TextView
            ) {
                mealsViewModel.currentPassenger.observe(viewLifecycleOwner) { id ->
                    val isCurrentPassenger = id == passenger.id
                    isCurrentPassenger.setPassengerBackground(cardPassenger, tvOrder, tvPassenger)
                }
            }

            override fun setOrderText(passenger: PassengerInputModel, tvOrder: TextView) {
                addsOnViewModel.passengerMealsList.observe(viewLifecycleOwner) { passengerMeals ->
                    val index = passengerMeals.indexOfFirst { it.id == passenger.id }

                    if (index != -1) {
                        val meals = passengerMeals[index].meals
                        val totalItem = meals.size
                        val totalPrice = meals.sumOf { it.price }
                        val orderText =
                            when (totalItem > 0) {
                                true ->
                                    getString(
                                        com.rajawali.common_resource.R.string.tv_meals_and_total_price_value,
                                        totalItem,
                                        totalPrice
                                    )

                                false ->
                                    getString(com.rajawali.common_resource.R.string.no_orders_yet)
                            }

                        tvOrder.text = orderText
                    }
                }

            }

        })
    }

    private fun Boolean.setPassengerBackground(
        cardPassenger: MaterialCardView,
        tvOrder: TextView,
        tvPassenger: TextView
    ) {
//        val white = resources.getColor(R.color.white)
        val alto = resources.getColor(com.rajawali.common_resource.R.color.alto)
        val black = resources.getColor(com.rajawali.common_resource.R.color.black)
//        val quartenary_color = resources.getColor(R.color.quaternary_color)
        val main_color = resources.getColor(com.rajawali.common_resource.R.color.dodger_blue)

        cardPassenger.strokeColor = if (this) main_color else alto
//        cardPassenger.setCardBackgroundColor(if (this) quartenary_color  else white )
        tvOrder.setTextColor(if (this) main_color else black)
        tvPassenger.setTextColor(if (this) main_color else black)
    }

    private fun getMeals() {
        mealsViewModel.mealsList.observe(viewLifecycleOwner) { isMeals ->
            when (isMeals) {
                is CommonResult.Error -> {} //TODO()
                is CommonResult.Success -> {
                    val recyclerView = binding.rvMeals
                    val _adapter = MealsAdapter(mealsViewModel)
                    val _layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    _adapter.apply {
                        submitList(isMeals.data)
                    }

                    recyclerView.apply {
                        adapter = _adapter
                        layoutManager = _layoutManager
                        setHasFixedSize(true)
                    }

                    _adapter.onPassengerMealsClicked()

                }
            }
        }
    }

    private fun MealsAdapter.onPassengerMealsClicked() {
        this.setOnMealsClickCallback(object : MealsAdapter.OnMealsClickCallback {
            override fun onMealsClickCallback(currentUser: Int, meals: MealModel) {
                addsOnViewModel.setPassengerMeals(currentUser, meals.id, meals.price)
            }

            override fun setCheckBoxStatus(checkBox: CheckBox, meals: MealModel) {
                mealsViewModel.currentPassenger.observe(viewLifecycleOwner) { currentPassenger ->
                    addsOnViewModel.passengerMealsList.observe(viewLifecycleOwner) { passengerMeals ->
                        val passengerIndex =
                            passengerMeals.indexOfFirst { it.id == currentPassenger }

                        if (passengerIndex != -1) {
                            val mealsList = passengerMeals[passengerIndex].meals
                            checkBox.isChecked = mealsList.any { it.id == meals.id }
//                            checkBox.isChecked = passengerMeals[passengerIndex].meals.contains(meals.id)
                        } else
                            checkBox.isChecked = false
                    }
                }
            }
        })
    }
}