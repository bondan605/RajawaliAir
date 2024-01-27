package com.rajawali.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AirplaneModel(
    val id: String,

    val airplaneCode: String,
) : Parcelable