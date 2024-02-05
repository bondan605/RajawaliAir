package com.rajawali.app.presentation.chooseTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.model.BuyerContactModel
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.model.PassengerModel
import timber.log.Timber

class TicketViewModel : ViewModel() {

    private val _buyerContact = MutableLiveData<BuyerContactModel>()
    val buyerContact: LiveData<BuyerContactModel> = _buyerContact

    private val _isRoundTrip = MutableLiveData<Boolean>(false)
    val isRoundTrip: LiveData<Boolean> = _isRoundTrip

    private val _preferableDeparture = MutableLiveData<FindTicketModel>()
    val preferableDeparture: LiveData<FindTicketModel> = _preferableDeparture

    private val _preferableReturn = MutableLiveData<FindTicketModel>()
    val preferableReturn: LiveData<FindTicketModel> = _preferableReturn

    private val _departureTicket = MutableLiveData<FlightModel>()
    val departureTicket: LiveData<FlightModel> = _departureTicket

    private val _returnTicket = MutableLiveData<FlightModel>()
    val returnTicket: LiveData<FlightModel> = _returnTicket

    private val _passenger = MutableLiveData<List<PassengerModel>>()
    val passenger: LiveData<List<PassengerModel>> = _passenger

    fun setBuyerContact(
        genderType: String,
        fullName: String,
        email: String,
        phoneNumber: String,
    ) {
        val ticket = preferableDeparture.value
        if (ticket != null) {
            val contact = BuyerContactModel(
                classType = ticket.seatType,
                genderType = genderType,
                fullName = fullName,
                email = email,
                phoneNumber = phoneNumber,
            )

            _buyerContact.value = contact
        }
    }

    fun setDepartureTicket(ticket: FlightModel) {
        Timber.d("setDepartureTicket $ticket")
        _departureTicket.value = ticket
    }

    fun setReturnTicket(ticket: FlightModel) {
        _returnTicket.value = ticket
    }

    fun setPreferableDeparture(preferable: FindTicketModel?) {
        if (preferable != null)
            _preferableDeparture.value = preferable!!
    }

    fun setPreferableReturn(preferable: FindTicketModel?) {
        if (preferable != null)
            _preferableReturn.value = preferable!!
    }

    fun deleteMatchingPassenger(
        id: Int,
        age: PassengerCategoryEnum,
        fullName: String,
        gender: String,
        checkBoxId: CourtesyEnum
    ) {
        val passengerList = _passenger.value?.toMutableList() ?: mutableListOf()
        val matching = PassengerModel(
            id = id,
            ageType = age,
            fullName = fullName,
            genderType = gender,
            checkBoxId = checkBoxId

        )

        passengerList.remove(matching)

        _passenger.value = passengerList
    }

    fun setPassenger(
        id: Int,
        genderType: String,
        fullName: String,
        ageType: PassengerCategoryEnum,
        checkBoxId: CourtesyEnum,
    ) {
        val currentIndex = id - 1
        val passengerList = _passenger.value?.toMutableList() ?: mutableListOf()

        if (currentIndex < passengerList.size) {
            val updatePassenger = PassengerModel(
                passengerList[currentIndex].id,
                genderType,
                fullName,
                passengerList[currentIndex].ageType,
                checkBoxId
            )

            passengerList[currentIndex] = updatePassenger
        } else {
            val newPassenger = PassengerModel(
                id,
                genderType,
                fullName,
                ageType,
                checkBoxId
            )
            passengerList.add(newPassenger)

        }

        _passenger.value = passengerList
    }

    fun setRoundTrip(value: Boolean) {
        _isRoundTrip.value = value
    }
}