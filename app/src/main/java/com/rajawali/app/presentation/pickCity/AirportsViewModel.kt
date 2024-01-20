package com.rajawali.app.presentation.pickCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.model.SearchModel

class AirportsViewModel: ViewModel() {

    private val _departureAirport = MutableLiveData<SearchModel>()
    val departureAirport : LiveData<SearchModel> = _departureAirport

    private val _arrivingAirport = MutableLiveData<SearchModel>()
    val arrivingAirport : LiveData<SearchModel> = _arrivingAirport

    fun setDepartureAirport(airport: SearchModel) {
        _departureAirport.value = airport
    }

    fun setArrivingAirport(airport: SearchModel) {
        _arrivingAirport.value = airport
    }
}