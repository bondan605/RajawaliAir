package com.rajawali.app.presentation.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.PassengerClassEnum
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.enums.TripValueEnum
import java.time.LocalDate

class TripViewModel : ViewModel() {

    //init is required to be false for it to work.
    private val _roundTrip = MutableLiveData<Boolean>(false)
    val roundTrip: LiveData<Boolean> = _roundTrip

    private val _departureDate = MutableLiveData<String>()
    val departureDate: LiveData<String> = _departureDate

    private val _returnDate = MutableLiveData<String>()
    val returnDate: LiveData<String> = _returnDate

    private val _isValueEmpty = MutableLiveData<List<TripValueEnum>>()
    val isValueEmpty: LiveData<List<TripValueEnum>> = _isValueEmpty

    fun setDepartureTicket(
        departureCityCode: String,
        departureDate: String,
        destinationCityCode: String,
        seatType: PassengerClassEnum,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
    ): FindTicketModel {
        return FindTicketModel(
            departureCityCode = departureCityCode,
            departureDate = departureDate,
            destinationCityCode = destinationCityCode,
            seatType = seatType,
            adultPassenger = adultPassenger,
            childPassenger = childPassenger,
            infantPassenger = infantPassenger,
        )
    }

    fun setReturnTicket(
        departureCityCode: String,
        returnDate: String,
        destinationCityCode: String,
        seatType: PassengerClassEnum,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
    ): FindTicketModel {
        return FindTicketModel(
            departureCityCode = departureCityCode,
            departureDate = returnDate,
            destinationCityCode = destinationCityCode,
            seatType = seatType,
            adultPassenger = adultPassenger,
            childPassenger = childPassenger,
            infantPassenger = infantPassenger,
        )
    }

    fun isValueEmpty(
        departureCityCode: String,
        departureDate: String,
        destinationCityCode: String,
        seatType: PassengerClassEnum,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
    ) {
        val value = mutableListOf<TripValueEnum>()

        if (!departureCityCode.isValueEmpty())
            value.add(TripValueEnum.DEPARTURE_CITY)

        if (!departureDate.isValueEmpty())
            value.add(TripValueEnum.DEPARTURE_DATE)

        if (!destinationCityCode.isValueEmpty())
            value.add(TripValueEnum.DESTINATION_CITY)

        if (!seatType.isValueEmpty())
            value.add(TripValueEnum.SEAT_TYPE)

        if (adultPassenger.isValueEmpty() && childPassenger.isValueEmpty() && infantPassenger.isValueEmpty())
            value.add(TripValueEnum.PASSENGER)

        if (value.isEmpty())
            value.add(TripValueEnum.NULL)

        _isValueEmpty.value = value
    }

    private fun Any.isValueEmpty(): Boolean {
        return when (this) {
            is String ->
                this.isNotEmpty()

            is Int ->
                this == 0

            is PassengerClassEnum ->
                this != PassengerClassEnum.NULL

            else ->
                true

        }
    }

    fun isRoundTrip(choice: Boolean) {
        _roundTrip.value = choice
    }

    fun setDepartureDate(date: LocalDate) {
        _departureDate.value = date.toString()
    }

    fun setReturnDate(date: LocalDate) {
        _returnDate.value = date.toString()
    }
}