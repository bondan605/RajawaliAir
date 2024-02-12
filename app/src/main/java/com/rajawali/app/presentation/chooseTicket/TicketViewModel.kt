package com.rajawali.app.presentation.chooseTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.model.BuyerContactModel
import com.rajawali.core.domain.model.FindTicketModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.model.PassengerModel
import com.rajawali.core.domain.model.PaymentRecordModel
import com.rajawali.core.domain.model.ReservationModel
import com.rajawali.core.util.DateFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

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

    private val _reservation = MutableLiveData<ReservationModel>()
    val reservation: LiveData<ReservationModel> = _reservation

    private val _payment = MutableLiveData<PaymentRecordModel>()
    val payment: LiveData<PaymentRecordModel> = _payment

    private val _paymentTimer = MutableLiveData<Long>()
    val paymentTimer: LiveData<Long> = _paymentTimer

    fun startTimer(end: String) {
        val targetDateTime = DateFormat.stringToLocalDateTime(end)

        _paymentTimer.postValue(calculateRemainingTime(targetDateTime))
        launchTimer()
    }

    private fun launchTimer() {
        val duration = paymentTimer.value ?: 0
        viewModelScope.launch {
            while (duration > 0) {
                val remainingTime = paymentTimer.value ?: 0
                _paymentTimer.postValue(remainingTime - 1000L) // Use postValue from background thread
//                duration -= 1000L
                delay(1000L)
            }
        }
    }

    private fun calculateRemainingTime(targetDateTime: LocalDateTime): Long {
        val now = LocalDateTime.now()
        val duration = Duration.between(now, targetDateTime)
        return if (duration.isNegative) {
            0L // Timer has already passed
        } else {
            duration.toMillis()
        }
    }

    fun timerLongToLocalTime(value: Long): LocalTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault()).toLocalTime()

    fun setPayment(data: PaymentRecordModel) {
        _payment.value = data
    }

    fun setReservation(data: ReservationModel) {
        _reservation.value = data
    }

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
        val passengerList = passenger.value?.toMutableList() ?: mutableListOf()
        val isIndex = passengerList.indexOfFirst { it.id == id }

        if (isIndex != -1) {
            val updatePassenger = passengerList[isIndex].copy(
                fullName = fullName,
                genderType = genderType,
                checkBoxId = checkBoxId
            )
            passengerList[isIndex] = updatePassenger
        } else {
            // Passenger not found, add a new one
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