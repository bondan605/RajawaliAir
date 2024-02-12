package com.rajawali.core.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.BaggageEnum
import com.rajawali.core.domain.model.CreateReservationPassengerDetailModel
import com.rajawali.core.domain.model.PassengerBaggageModel
import com.rajawali.core.domain.model.PassengerMealsModel
import com.rajawali.core.domain.model.PassengerSeatModel
import com.rajawali.core.domain.model.PreferredMeal
import com.rajawali.core.util.DataMapper

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

    private val _totalItem = MutableLiveData<Map<String, Int>>()
    val totalItem: LiveData<Map<String, Int>> = _totalItem

    private val _totalBaggagePrice = MutableLiveData<Int>(0)
    val totalBaggagePrice: LiveData<Int> = _totalBaggagePrice

    private val _passengerBaggageList = MutableLiveData<List<PassengerBaggageModel>>()
    val passengerBaggageList: LiveData<List<PassengerBaggageModel>> = _passengerBaggageList

    private val _passengerMealsList = MutableLiveData<List<PassengerMealsModel>>()
    val passengerMealsList: LiveData<List<PassengerMealsModel>> = _passengerMealsList

    private val _totalMealsPrice = MutableLiveData<Int>(0)
    val totalMealsPrice: LiveData<Int> = _totalMealsPrice

    private val _priceDetailsdropdownBtnState = MutableLiveData<Boolean>(false)
    val priceDetailsDropdownBtnState: LiveData<Boolean> = _priceDetailsdropdownBtnState

    private val _travelInsuranceDetailsdropdownBtnState = MutableLiveData<Boolean>(false)
    val travelInsuranceDetailsDropdownBtnState: LiveData<Boolean> =
        _travelInsuranceDetailsdropdownBtnState

    private val _seats = MutableLiveData<List<PassengerSeatModel>>()
    val seats: LiveData<List<PassengerSeatModel>> = _seats

//    private val _seats = MutableLiveData<Map<Int, String>>()
//    val seats : LiveData<Map<Int, String>> = _seats

//    fun setPassengerSeat(id : Int, seat: String) {
////        val currentSeats = seats.value?.toMutableList() ?: mutableListOf()
//        val currentSeats = seats.value?.toMutableMap() ?: mutableMapOf()
//
//        currentSeats[id] = seat
//
//        _seats.value = currentSeats
//    }

    private fun setDefaultBaggage(passengers : Int) {
        val baggages = passengerBaggageList.value?.toMutableList() ?: mutableListOf()

        for (passenger in 1..passengers) {
            val baggage = PassengerBaggageModel(
                id = passenger,
            )
            baggages.add(baggage)
        }

        _passengerBaggageList.value = baggages
    }

    private fun baggageEnumToString(baggage: BaggageEnum) : String =
        DataMapper.baggageEnumToInt(baggage)

    fun combinePassengerDetails(
        mealsList: List<PassengerMealsModel>,
        seatList: List<PassengerSeatModel>,
        baggageList: List<PassengerBaggageModel>
    ): List<CreateReservationPassengerDetailModel> {
        val combinedList = mutableListOf<CreateReservationPassengerDetailModel>()

        seatList.forEach { seats ->
            val seat = seatList.find { it.id == seats.id }?.seat ?: ""
            val baggageEnum = baggageList.find { it.id == seats.id }?.baggage ?: BaggageEnum.KG0
            val baggage = baggageEnumToString(baggageEnum)
            val mealsModel = mealsList.find { it.id == seats.id }
            val mealNames = mealsModel?.meals?.map { it.id } ?: mutableListOf()

            combinedList.add(
                CreateReservationPassengerDetailModel(
                    seatId = seat,
                    baggage = baggage,
                    meals = mealNames
                )
            )
        }

        return combinedList
    }

    fun setPassengerSeat(id: Int, preferredSeat: String) {
        val currentSeat = seats.value?.toMutableList() ?: mutableListOf()

        val isIndex = currentSeat.indexOfFirst { it.id == id }

        if (isIndex != -1) {
            val updateSeat = currentSeat[isIndex].copy(
                seat = preferredSeat
            )
            currentSeat[isIndex] = updateSeat
        } else {
            // Passenger not found, add a new one
            val newSeat = PassengerSeatModel(
                id = id,
                seat = preferredSeat
            )
            currentSeat.add(newSeat)
        }

        _seats.value = currentSeat
    }

    fun setPriceDetailsDropdownState() {
        val state = priceDetailsDropdownBtnState.value ?: false

        _priceDetailsdropdownBtnState.value = !state
    }

    fun setTravelInsuranceDetailsDropdownState() {
        val state = travelInsuranceDetailsDropdownBtnState.value ?: false

        _travelInsuranceDetailsdropdownBtnState.value = !state
    }

    fun setTotalMealsPrice(passengerMeals: List<PassengerMealsModel>) {
        val totalPrice = passengerMeals.flatMap { it.meals }.sumOf { it.price }

        _totalMealsPrice.value = totalPrice
    }

    fun setPassengerMeals(id: Int, meal: String, price: Int) {
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

    private fun updateMeals(
        existingMeals: List<PreferredMeal>,
        preferredMeal: PreferredMeal
    ): List<PreferredMeal> =
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
        var totalPrice = 0

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

    fun setTotalItem(key: String, value: Int) {
        val currentItem = _totalItem.value?.toMutableMap() ?: mutableMapOf()

        currentItem[key] = value

        _totalItem.value = currentItem
    }

    fun setTotalPrice(items: Map<String, Int>) {
        val totalPrice = items.values.sumOf { it }

        _totalPrice.value = totalPrice
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