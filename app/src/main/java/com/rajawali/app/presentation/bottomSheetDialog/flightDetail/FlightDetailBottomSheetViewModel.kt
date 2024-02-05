package com.rajawali.app.presentation.bottomSheetDialog.flightDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.GenderEnum

class FlightDetailBottomSheetViewModel : ViewModel() {

    private val _gender = MutableLiveData<GenderEnum>()
    val gender: LiveData<GenderEnum> = _gender

    private val _checkBoxId = MutableLiveData<CourtesyEnum>()
    val checkBoxId: LiveData<CourtesyEnum> = _checkBoxId

    fun setGenderAndId(gender: GenderEnum, id: CourtesyEnum) {
        _gender.value = gender
        _checkBoxId.value = id
    }
}