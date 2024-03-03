package com.rajawali.app.presentation.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.PassengerClassEnum
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.enums.TripValueEnum
import java.time.LocalDate

@Suppress("RemoveExplicitTypeArguments")
class TripViewModel : ViewModel() {

    //init is required to be false for it to work.
    private val _roundTrip = MutableLiveData<Boolean>(false)
    val roundTrip: LiveData<Boolean> = _roundTrip

    private val _departureDate = MutableLiveData<LocalDate>()
    val departureDate: LiveData<LocalDate> = _departureDate

    private val _returnDate = MutableLiveData<LocalDate>()
    val returnDate: LiveData<LocalDate> = _returnDate

    private val _isValueEmpty = MutableLiveData<List<TripValueEnum>>()
    val isValueEmpty: LiveData<List<TripValueEnum>> = _isValueEmpty

    fun setTicket(
        departureId: String,
        departureDate: LocalDate,
        destinationId: String,
        seatType: PassengerClassEnum,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureCity : String,
        destinationCity : String,
    ): FindTicketModel {
        return FindTicketModel(
            departureId = departureId,
            departureDate = departureDate,
            destinationId = destinationId,
            seatType = seatType,
            adultPassenger = adultPassenger,
            childPassenger = childPassenger,
            infantPassenger = infantPassenger,
            departureCity = departureCity,
            destinationCity = destinationCity
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
        _departureDate.value = date
    }

    fun setReturnDate(date: LocalDate) {
        _returnDate.value = date
    }
}