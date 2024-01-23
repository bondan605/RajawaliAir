package com.rajawali.app.presentation.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TripViewModel : ViewModel() {

    private val _roundTrip = MutableLiveData<Boolean>()
    val roundTrip: LiveData<Boolean> = _roundTrip

    fun isRoundTrip(choice: Boolean) {
        _roundTrip.value = choice
    }
}