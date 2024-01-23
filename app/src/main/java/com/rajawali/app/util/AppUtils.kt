package com.rajawali.app.util

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.findNavController

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
}