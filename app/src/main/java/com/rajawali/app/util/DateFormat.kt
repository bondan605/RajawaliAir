package com.rajawali.app.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object DateFormat {

    val currentDate = LocalDate.now()

    /*
    *   pattern EEEE, dd MMMM yyyy for full date.
    *       example: Sabtu, 20 Januari 2024
    *   pattern EEE, dd MMM yyyy for half the text of day and month
    *       example: Sab, 20 Jan 2024
    * */
    fun formatToIndonesiaLanguage(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))

    fun formatToFullDateWithDay(date: String): String {
        val slicedDate = date.take(10)
        val localDate = LocalDate.parse(slicedDate)
        return localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))
    }

    fun longToLocalDate(long: Long): LocalDate {
        val instant = Instant.ofEpochMilli(long)
        //this need minimum of API 34
//        return LocalDate.ofInstant(instant, ZoneId.systemDefault())
        return instant.atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun longToLocalDateTime(long: Long): LocalDateTime {
        val instant = Instant.ofEpochMilli(long)

        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}