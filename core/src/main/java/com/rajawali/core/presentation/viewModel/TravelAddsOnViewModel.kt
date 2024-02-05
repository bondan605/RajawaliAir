package com.rajawali.core.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.AddsOnEnum
import com.rajawali.core.domain.enums.BaggageEnum
import com.rajawali.core.domain.model.Insurance
import com.rajawali.core.domain.model.PassengerBaggageModel
import com.rajawali.core.domain.model.PassengerMealsModel
import com.rajawali.core.domain.model.PreferredMeal

@Suppress("RemoveExplicitTypeArguments")
class TravelAddsOnViewModel : ViewModel() {

    private val _baggage = MutableLiveData<Boolean>(false)
    val baggage: LiveData<Boolean> = _baggage

    private val _seatNumber = MutableLiveData<Boolean>(false)
    val seatNumber: LiveData<Boolean> = _seatNumber

    private val _meals = MutableLiveData<Boolean>(false)
    val meals: LiveData<Boolean> = _meals

    private val _travelInsurance = MutableLiveData<Boolean>(false)
    val travelInsurance: LiveData<Boolean> = _travelInsurance

    private val _baggageInsurance = MutableLiveData<Boolean>(false)
    val baggageInsurance: LiveData<Boolean> = _baggageInsurance

    private val _flightDelayInsurance = MutableLiveData<Boolean>(false)
    val flightDelayInsurance: LiveData<Boolean> = _flightDelayInsurance

    private val _totalPrice = MutableLiveData<Int>(0)
    val totalPrice: LiveData<Int> = _totalPrice

    private val _totalBaggagePrice = MutableLiveData<Int>(0)
    val totalBaggagePrice: LiveData<Int> = _totalBaggagePrice

    private val _passengerBaggageList = MutableLiveData<List<PassengerBaggageModel>>()
    val passengerBaggageList: LiveData<List<PassengerBaggageModel>> = _passengerBaggageList

    private val _passengerMealsList = MutableLiveData<List<PassengerMealsModel>>()
    val passengerMealsList: LiveData<List<PassengerMealsModel>> = _passengerMealsList

    private val _totalMealsPrice = MutableLiveData<Int>()
    val totalMealsPrice: LiveData<Int> = _totalMealsPrice

    fun setTotalMealsPrice(passengerMeals: List<PassengerMealsModel>) {
        val totalPrice = passengerMeals.flatMap { it.meals }.sumOf { it.price }

        _totalMealsPrice.value = totalPrice
    }
    fun setPassengerMeals(id: Int, meal: String, price : Int) {
        val passengerMealsList = _passengerMealsList.value?.toMutableList() ?: mutableListOf()
        val preferredMeal = PreferredMeal(meal, price)

        // Find the passenger by ID in the list
        val passengerIndex = passengerMealsList.indexOfFirst { it.id == id }

        if (passengerIndex != -1) {
            // Passenger found, update their meals
            val updatedPassenger = passengerMealsList[passengerIndex].copy(
                meals = updateMeals(passengerMealsList[passengerIndex].meals, preferredMeal)
            )
            passengerMealsList[passengerIndex] = updatedPassenger
        } else {
            // Passenger not found, add a new one
            val newPassengerMeals = PassengerMealsModel(
                id = id,
                meals = mutableListOf(preferredMeal)
            )
            passengerMealsList.add(newPassengerMeals)
        }

        _passengerMealsList.value = passengerMealsList
    }

    private fun updateMeals(existingMeals: List<PreferredMeal>, preferredMeal: PreferredMeal): List<PreferredMeal> =
        if (existingMeals.contains(preferredMeal))
            existingMeals.filterNot { it == preferredMeal }
        else
            existingMeals + preferredMeal

    fun setBaggageList(
        id: Int,
        baggage: BaggageEnum
    ) {
        val currentIndex = id - 1
        val baggageList = _passengerBaggageList.value?.toMutableList() ?: mutableListOf()

        if (currentIndex < baggageList.size) {
            val updateBaggage = PassengerBaggageModel(
                id = baggageList[currentIndex].id,
                baggage = baggage
            )

            baggageList[currentIndex] = updateBaggage
        } else {
            val newPassenger = PassengerBaggageModel(
                id = id,
                baggage = baggage
            )
            baggageList.add(newPassenger)

        }

        _passengerBaggageList.value = baggageList
    }

    fun setTotalBaggagePrice(baggageList: List<PassengerBaggageModel>) {
        var totalPrice: Int = 0

        baggageList.map { passenger ->
            totalPrice += when (passenger.baggage) {
                BaggageEnum.KG0 ->
                    0

                BaggageEnum.KG5 ->
                    250000

                BaggageEnum.KG10 ->
                    500000

                BaggageEnum.KG20 ->
                    1000000

                BaggageEnum.KG30 ->
                    2000000

                BaggageEnum.KG40 ->
                    2000000
            }
        }
        _totalBaggagePrice.value = totalPrice
    }


    fun setTotalPrice(addsOnType: AddsOnEnum, addsOn: Int = 0, checkBox: Boolean = false) {
        var currentValue = totalPrice.value ?: 0
        val insurancePrice = Insurance.PriceList

        when (addsOnType) {
            AddsOnEnum.BAGGAGE ->
                currentValue += addsOn

            AddsOnEnum.SEAT ->
                currentValue += addsOn

            AddsOnEnum.MEAL ->
                currentValue += addsOn

            AddsOnEnum.TRAVEL_INSURANCE ->
                currentValue =
                    checkBox.onInsuranceClicked(currentValue, insurancePrice.travelInsurance)

            AddsOnEnum.BAGGAGE_INSURANCE ->
                currentValue =
                    checkBox.onInsuranceClicked(currentValue, insurancePrice.baggageInsurance)

            AddsOnEnum.DELAY_INSURANCE ->
                currentValue =
                    checkBox.onInsuranceClicked(currentValue, insurancePrice.flightDelayInsurance)
        }

        _totalPrice.value = currentValue
    }

    private fun Boolean.onInsuranceClicked(totalPrice: Int, price: Int): Int =
        when (this) {
            true ->
                totalPrice + price

            false ->
                totalPrice - price
        }


    fun setTravelInsurance() {
        val currentValue = travelInsurance.value ?: false
        _travelInsurance.value = !currentValue
    }

    fun setBaggageInsurance() {
        val currentValue = baggageInsurance.value ?: false
        _baggageInsurance.value = !currentValue
    }

    fun setDelayInsurance() {
        val currentValue = flightDelayInsurance.value ?: false
        _flightDelayInsurance.value = !currentValue
    }

    fun setBaggage() {
        val currentValue = baggage.value ?: false
        _baggage.value = !currentValue
    }

    fun setSeat() {
        val currentValue = seatNumber.value ?: false
        _seatNumber.value = !currentValue
    }

    fun setMeals() {
        val currentValue = meals.value ?: false
        _meals.value = !currentValue
    }
}