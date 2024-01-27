package com.rajawali.app.presentation.chooseTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.domain.usecase.GetPreferredFlight

class ChooseTicketViewModel(private val useCase: GetPreferredFlight) : ViewModel() {

    private val _departureTicket = MutableLiveData<FlightModel>()
    val departureTicket: LiveData<FlightModel> = _departureTicket

    fun setDepartureTicket(ticket: FlightModel) {
        _departureTicket.value = ticket
    }


    fun getPreferredTickets(
        departure: String,
        destination: String,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureDate: String,
        seatType: String,
    ): LiveData<UCResult<List<FlightModel>>> =
        useCase.getPreferredFlight(
            departure = departure,
            destination = destination,
            adultPassenger = adultPassenger,
            childPassenger = childPassenger,
            infantPassenger = infantPassenger,
            departureDate = departureDate,
            seatType = seatType,
        ).asLiveData()
}