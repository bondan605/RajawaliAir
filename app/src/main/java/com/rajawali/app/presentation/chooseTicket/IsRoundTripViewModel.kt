package com.rajawali.app.presentation.chooseTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IsRoundTripViewModel : ViewModel() {

    private val _isRoundTrip = MutableLiveData<Boolean>()
    val isRoundTrip : LiveData<Boolean> = _isRoundTrip

    fun setRoundTrip(value : Boolean) {
        _isRoundTrip.value = value
    }
}