package com.rajawali.app.presentation.chooseTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.tickets.GetFlightByDate
import com.rajawali.core.domain.usecase.tickets.GetPreferredFlight
import java.time.LocalDate

@Suppress("RemoveExplicitTypeArguments")
class ChooseTicketViewModel(
    private val useCase: GetPreferredFlight,
    private val dateUseCase: GetFlightByDate
) : ViewModel() {

    private val _departureTicket = MutableLiveData<FlightModel>()
    val departureTicket: LiveData<FlightModel> = _departureTicket

    private val _isDeparturePicked = MutableLiveData<Boolean>(false)
    val isDeparturePicked: LiveData<Boolean> = _isDeparturePicked

    fun setDepartureTicket(ticket: FlightModel) {
        _departureTicket.value = ticket
    }

    fun setDeparturePicked() {
        val value = isDeparturePicked.value ?: false
        _isDeparturePicked.value = !value
    }

    fun getDates(preferredDate: LocalDate): LiveData<CommonResult<List<LocalDate>>> =
        dateUseCase.createDates(preferredDate).asLiveData()


    fun getPreferredTickets(
        departure: String,
        destination: String,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureDate: LocalDate,
        seatType: String,
    ): LiveData<CommonResult<List<FlightModel>>> =
        useCase.getPreferredFlight(
            departure = departure,
            destination = destination,
            adultPassenger = adultPassenger,
            childPassenger = childPassenger,
            infantPassenger = infantPassenger,
            departureDate = departureDate.toString(),
            seatType = seatType,
        ).asLiveData()
}