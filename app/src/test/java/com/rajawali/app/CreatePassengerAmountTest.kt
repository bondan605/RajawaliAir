package com.rajawali.app

import com.rajawali.core.domain.model.PassengerInputModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.domain.usecase.CreatePassengerInformationInputUseCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CreatePassengerAmountTest {

    @Test
    fun test_create_passenger_list_with_correct_number() = runTest {
        // Arrange
        val useCase = CreatePassengerInformationInputUseCase()
        val adult = 2
        val child = 1
        val infant = 1

        // Act
        val result = useCase.createPassengerDetail(adult, child, infant).toList()

        // Assert
        assertEquals(UCResult.Success::class, result[0]::class)
        val passengerList = (result[0] as UCResult.Success<List<PassengerInputModel>>).data
        assertEquals(adult + child + infant, passengerList.size)
    }
}