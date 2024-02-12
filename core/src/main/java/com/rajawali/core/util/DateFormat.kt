package com.rajawali.core.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs


object DateFormat {

    val currentDate = LocalDate.now()
    fun stringToLocalDateTime(expired: String): LocalDateTime {
        if (expired.length >= 18) {
            val sliced = expired.slice(0..18)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            return LocalDateTime.parse(sliced, formatter)
        }
        return LocalDateTime.now()
    }

    /*
    *   pattern EEEE, dd MMMM yyyy for full date.
    *       example: Sabtu, 20 Januari 2024
    *   pattern EEE, dd MMM yyyy for half the text of day and month
    *       example: Sab, 20 Jan 2024
    * */
    fun formatDateToAbbreviatedString(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))

    fun formatDateStringToAbbreviatedString(date: String): String {
        val slicedDate = date.sliceDate()
        val localDate = LocalDate.parse(slicedDate)

        return localDate.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))
    }


    fun String.sliceDate(): String =
        this.take(10)

    fun formatToFullDateWithDay(date: String): String {
        val slicedDate = date.sliceDate()
        val localDate = LocalDate.parse(slicedDate)

        return localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))
    }

    fun formatDateAndMonthOnly(date: String): String {
        val slicedDate = date.sliceDate()
        val localDate = LocalDate.parse(slicedDate)

        return localDate.format(DateTimeFormatter.ofPattern("dd MMM"))
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

    fun calculateTimeDifference(time1Str: String, time2Str: String): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault()) // Ensure consistent format

        try {
            val date1 = format.parse(time1Str)
            val date2 = format.parse(time2Str)

            // Create Calendar instances from Dates
            val calendar1 = Calendar.getInstance()
            calendar1.time = date1
            val calendar2 = Calendar.getInstance()
            calendar2.time = date2

            // Set the same day for accurate comparison
            if (calendar1.get(Calendar.DAY_OF_YEAR) != calendar2.get(Calendar.DAY_OF_YEAR)) {
                if (calendar1.before(calendar2)) {
                    calendar2.add(Calendar.YEAR, -1) // Ensure correct handling of year crossing
                } else {
                    calendar1.add(Calendar.YEAR, 1)
                }
            }

            // Calculate absolute hour and minute differences
            val hourDiff =
                abs(calendar1.get(Calendar.HOUR_OF_DAY) - calendar2.get(Calendar.HOUR_OF_DAY))
            val minuteDiff = abs(calendar1.get(Calendar.MINUTE) - calendar2.get(Calendar.MINUTE))

            // Combine into "x h y m" format, handling 0 hour edge case
            val result = "%d h %d m".format(hourDiff, minuteDiff)
            return if (hourDiff == 0) "$minuteDiff m" else result // Handle 0 hours gracefully
        } catch (e: ParseException) {
            e.printStackTrace()
            return "Invalid time format" // Provide a meaningful error message
        }
    }

    fun getDayAndDateOnly(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("EEE, dd"))


}