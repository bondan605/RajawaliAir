package com.rajawali.app.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

object NavigationUtils {
    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }

    fun NavController.safeNavigateUsingID(@IdRes direction: Int, bundle: Bundle = Bundle()) {
        when (bundle.isEmpty) {
            true ->
                currentDestination?.getAction(direction)?.run {
                    navigate(direction)
                }

            false ->
                currentDestination?.getAction(direction)?.run {
                    navigate(direction, bundle)
                }
        }
    }
}