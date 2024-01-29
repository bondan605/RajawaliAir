package com.rajawali.app.util

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.findNavController
import com.rajawali.app.R
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.enums.PassengerClassEnum
import java.util.Locale

object AppUtils {

    fun navigate(view: View?, @IdRes destination: Int) {
        view?.findNavController()?.navigate(destination)
    }

    fun getNumberFromString(value: String): String =
        value.filter { it.isDigit() }

    fun isNumberExist(value: String): Int =
        if (value.isEmpty())
            0
        else
            value.toInt()

    fun capitalize(string: String) : String =
        string.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    fun Context?.getPassengerClassText(passenger: PassengerClassEnum?): String {
        return if (this != null)
            when (passenger) {
                PassengerClassEnum.ECONOMY ->
                    this.getString(R.string.tv_passenger_economy_class)


                PassengerClassEnum.BUSINESS ->
                    this.getString(R.string.tv_passenger_business_class)


                PassengerClassEnum.FIRST ->
                    this.getString(R.string.tv_passenger_first_class)

                else -> ""
            }
        else
            ""

    }

    fun Context?.isPassengerExist(
        initialText: String,
        initialCount: Int?,
        passenger: PassengerCategoryEnum
    ): String {
        val count = initialCount ?: 0
        var text = initialText

        if (count > 0) {
            text += text.addVerticalLine()
            text += when (passenger) {
                PassengerCategoryEnum.ADULT ->
                    this?.getString(R.string.tv_passenger_adult_category, count)

                PassengerCategoryEnum.CHILD ->
                    this?.getString(R.string.tv_passenger_child_category, count)

                PassengerCategoryEnum.INFANT ->
                    this?.getString(R.string.tv_passenger_infant_category, count)
            }
        }

        return text
    }

    private fun String.addVerticalLine(): String =
        if (this.isNotEmpty())
            "$this | "
        else
            this


}