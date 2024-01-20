package com.rajawali.core.domain.usecase

import android.util.Log
import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAirportsUseCase(private val repository: RemoteRepository) {

//    fun getAirports(): Flow<UCResult<List<SearchModel>>> = flow {
//        val response = repository.getAirports()
//
//        when (response) {
//            is ApiResponse.Success -> {
//                val domain = response.data.map {
//                    DataMapper.airportsResponseToSearchDomain(it)
//                }
//                emit(UCResult.Success(domain))
//            }
//
//            is ApiResponse.Error -> {
//                UCResult.Error(response.errorMessage)
//            }
//
//            ApiResponse.Empty -> {
//                UCResult.Error(Constant.DATA_EMPTY)
//            }
//        }
//    }

    suspend fun getAllAirports(): UCResult<List<SearchModel>> {
        val response = repository.getAirports()

        return when (response) {
            is ApiResponse.Success -> {
                val domain = response.data.map {
                    DataMapper.airportsResponseToSearchDomain(it)
                }
                Log.d("GetAirportsUseCase", domain.toString())
                UCResult.Success(domain)
            }

            is ApiResponse.Error -> {
                UCResult.Error(response.errorMessage)
            }

        }
    }

    fun getSearchedAirport(keyword: String): Flow<UCResult<List<SearchModel>>> = flow {
        val airports = getAllAirports()

        when (airports) {
            is UCResult.Success -> {
                val result = airports.data.filter {
                    //ignoreCase for ignoring word sensitivity. Very important
                    it.city.contains(keyword, ignoreCase = true)
                }
                Log.d("GetAirportsUseCase", result.toString())
                emit(UCResult.Success(result))
            }

            is UCResult.Error -> emit(UCResult.Error(airports.errorMessage))
        }
    }
}