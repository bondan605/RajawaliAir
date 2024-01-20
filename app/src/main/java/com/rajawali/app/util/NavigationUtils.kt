package com.rajawali.app.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

object NavigationUtils {
    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }

}