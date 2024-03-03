package com.rajawali.app.presentation.detailsInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.GenderEnum
import com.rajawali.core.domain.model.PassengerInputModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.tickets.CreatePassengerInformationInputUseCase

class DetailsInformationViewModel(private val createPassenger: CreatePassengerInformationInputUseCase) :
    ViewModel() {

    private val _gender = MutableLiveData<GenderEnum>()
    val gender: LiveData<GenderEnum> = _gender

    private val _checkBoxId = MutableLiveData<CourtesyEnum>()
    val checkBoxId: LiveData<CourtesyEnum> = _checkBoxId

    fun setGenderAndId(gender: GenderEnum, id: CourtesyEnum) {
        _gender.value = gender
        _checkBoxId.value = id
    }

    fun createPassengerInput(
        adult: Int,
        child: Int,
        infant: Int
    ): LiveData<CommonResult<List<PassengerInputModel>>> =
        createPassenger.createPassengerDetail(
            adult, child, infant
        ).asLiveData()

}