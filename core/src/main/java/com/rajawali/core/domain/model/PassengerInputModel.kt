package com.rajawali.core.domain.model

import android.os.Parcelable
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassengerInputModel(
    val id : Int,
    val age: PassengerCategoryEnum
) : Parcelable
