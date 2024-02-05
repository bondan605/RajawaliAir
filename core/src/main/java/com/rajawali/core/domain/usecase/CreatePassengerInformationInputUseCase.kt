package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.model.PassengerInputModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CreatePassengerInformationInputUseCase {
    private var id: Int = 1
    fun createPassengerDetail(
        adult: Int,
        child: Int,
        infant: Int
    ): Flow<UCResult<List<PassengerInputModel>>> = flow {
        id = 1

        try {
            val adultList =
                PassengerCategoryEnum.ADULT.createPassengerList(adult)
            val childList =
                PassengerCategoryEnum.CHILD.createPassengerList(child)
            val infantList =
                PassengerCategoryEnum.INFANT.createPassengerList(infant)
            val passenger = adultList + childList + infantList

            when (passenger.isNotEmpty()) {
                true ->
                    emit(UCResult.Success(passenger))

                false ->
                    throw Exception(Constant.DATA_EMPTY)
            }

        } catch (e: Exception) {
            Timber.w(e)
            emit(UCResult.Error(e.message ?: Constant.FETCH_FAILED))
        }
    }

    private fun PassengerCategoryEnum.createPassengerList(passenger: Int): MutableList<PassengerInputModel> {
        val list = mutableListOf<PassengerInputModel>()

        for (i in passenger downTo 1) {
            list.add(
                PassengerInputModel(
                    id = id,
                    age = this
                )
            )
            id += 1
        }

        return list
    }
}