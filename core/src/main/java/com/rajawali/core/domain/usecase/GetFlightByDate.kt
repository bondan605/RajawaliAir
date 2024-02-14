package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.enums.DateOrderEnum
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.LocalDate

class GetFlightByDate {

    fun createDates(preferredDate: LocalDate): Flow<CommonResult<List<LocalDate>>> = flow {
        try {

            val date = mutableListOf(preferredDate)
            val priorDate = create5Dates(preferredDate, DateOrderEnum.PRIOR)
            val afterDate = create5Dates(preferredDate, DateOrderEnum.AFTER)
            val dates = priorDate + date + afterDate

            if (priorDate.isNotEmpty() && afterDate.isNotEmpty())
                emit(CommonResult.Success(dates))
            else
                throw Exception()

        } catch (e: Exception) {
            Timber.w(e)
            emit(CommonResult.Error(Constant.DATA_EMPTY))
        }
    }

    private fun create5Dates(
        preferredDate: LocalDate,
        order: DateOrderEnum
    ): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()

        when (order) {
            DateOrderEnum.PRIOR -> {
                for (day in 5 downTo 1) {
                    val date = preferredDate.minusDays(day.toLong())
                    dates.add(date)
                }
            }

            DateOrderEnum.AFTER ->
                for (day in 1..5) {
                    val date = preferredDate.plusDays(day.toLong())
                    dates.add(date)
                }
        }

        return dates
    }

}