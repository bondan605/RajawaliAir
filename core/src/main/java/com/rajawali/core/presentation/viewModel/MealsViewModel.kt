package com.rajawali.core.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.MealModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.addOns.GetMealsUseCase

class MealsViewModel(meals : GetMealsUseCase) : ViewModel() {

    val mealsList : LiveData<CommonResult<List<MealModel>>> = meals.getMeals().asLiveData()

    private val _currentPassenger = MutableLiveData<Int>(1)
    val currentPassenger : LiveData<Int> = _currentPassenger

    fun setCurrentPassenger(id: Int) {
        _currentPassenger.value = id
    }
}