package com.rajawali.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateMapper {
    fun getDayAndDateOnly(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("EEE, dd"))


}