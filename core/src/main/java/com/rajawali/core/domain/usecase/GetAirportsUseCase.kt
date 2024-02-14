package com.rajawali.core.domain.usecase

import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAirportsUseCase(private val repository: RemoteRepository) {

    private suspend fun getAllAirports(): CommonResult<List<SearchModel>> {
        val response = repository.getAirports()

        return when (response) {
            is ApiResponse.Success -> {
                val domain = response.data.map {
                    DataMapper.airportsResponseToSearchDomain(it)
                }
                CommonResult.Success(domain)
            }

            is ApiResponse.Error -> {
                CommonResult.Error(response.errorMessage)
            }
        }
    }

    fun getSearchedAirport(keyword: String): Flow<CommonResult<List<SearchModel>>> = flow {
        val airports = getAllAirports()

        when (airports) {
            is CommonResult.Success -> {
                val searchResultByCity = getSearchedAirportByCity(keyword, airports.data)
                val searchResultByAirport = getSearchedAirportByAirport(keyword, airports.data)
                val combinedSearchResult = searchResultByCity.union(searchResultByAirport).toList()

                when (combinedSearchResult.isNotEmpty()) {
                    true ->
                        emit(CommonResult.Success(combinedSearchResult))

                    false ->
                        emit(CommonResult.Error(Constant.DATA_EMPTY))
                }
            }

            is CommonResult.Error -> emit(CommonResult.Error(airports.errorMessage))
        }
    }

    private fun getSearchedAirportByAirport(
        keyword: String,
        airports: List<SearchModel>
    ): List<SearchModel> =
        airports.filter {
            it.airport.contains(keyword, ignoreCase = true)
        }

    private fun getSearchedAirportByCity(
        keyword: String,
        airports: List<SearchModel>
    ): List<SearchModel> =
        airports.filter {
            it.city.contains(keyword, ignoreCase = true)
        }
}