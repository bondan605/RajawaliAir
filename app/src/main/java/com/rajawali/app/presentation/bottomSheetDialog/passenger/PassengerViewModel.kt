package com.rajawali.app.presentation.bottomSheetDialog.passenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajawali.core.domain.enums.ModifyCountTypeEnum
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.enums.PassengerClassEnum

class PassengerViewModel : ViewModel() {

    private val _adultPassengerCount = MutableLiveData(0)
    val adultPassengerCount: LiveData<Int> = _adultPassengerCount

    private val _childPassengerCount = MutableLiveData(0)
    val childPassengerCount: LiveData<Int> = _childPassengerCount

    private val _infantPassengerCount = MutableLiveData(0)
    val infantPassengerCount: LiveData<Int> = _infantPassengerCount

    private val _passengerClass = MutableLiveData<PassengerClassEnum>()
    val passengerClass: LiveData<PassengerClassEnum> = _passengerClass

    private val _onButtonDoneClickListener = MutableLiveData(false)
    val onButtonDoneClickListener: LiveData<Boolean> = _onButtonDoneClickListener


    fun setOnButtonDoneClickListener(value: Boolean) {
        _onButtonDoneClickListener.value = value
    }

    fun modifyPassengerClass(desiredPassengerClass: PassengerClassEnum) {
        _passengerClass.value = desiredPassengerClass
    }

    fun modifyPassengerCount(
        count: Int,
        modifyType: ModifyCountTypeEnum,
        category: PassengerCategoryEnum
    ) {
        when (category) {
            PassengerCategoryEnum.ADULT ->
                setAdultPassenger(count, modifyType)

            PassengerCategoryEnum.CHILD ->
                setChildPassenger(count, modifyType)

            PassengerCategoryEnum.INFANT ->
                setInfantPassenger(count, modifyType)
        }
    }

    private fun setAdultPassenger(count: Int, type: ModifyCountTypeEnum) {
        setPassenger(count, type, _adultPassengerCount)
    }

    private fun setChildPassenger(count: Int, type: ModifyCountTypeEnum) {
        setPassenger(count, type, _childPassengerCount)
    }

    private fun setInfantPassenger(count: Int, type: ModifyCountTypeEnum) {
        setPassenger(count, type, _infantPassengerCount)
    }

    private fun setPassenger(
        initialCount: Int,
        type: ModifyCountTypeEnum,
        passenger: MutableLiveData<Int>
    ) {
        val currentPassenger = passenger.value ?: 0

        when (type) {
            ModifyCountTypeEnum.ADD ->
                passenger.value = passenger.value?.plus(1) ?: 0

            ModifyCountTypeEnum.REMOVE -> {
                //user unable to remove passenger that already 0
                if (currentPassenger == 0)
                    return

                passenger.value = passenger.value?.minus(1) ?: 0
            }

            ModifyCountTypeEnum.STRAIGHT -> {
                //write 0 instead less than 0 when user write less than 0
                if (initialCount < 0) {
                    passenger.value = 0
                    return
                }

                passenger.value = initialCount
            }
        }
    }
}