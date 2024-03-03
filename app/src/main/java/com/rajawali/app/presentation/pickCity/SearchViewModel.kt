package com.rajawali.app.presentation.pickCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.domain.usecase.airports.AddRecentSearchUseCase
import com.rajawali.core.domain.usecase.airports.ClearRecentSearchUseCase
import com.rajawali.core.domain.usecase.airports.GetAirportsUseCase
import com.rajawali.core.domain.usecase.airports.GetRecentSearchUseCase
import kotlinx.coroutines.launch


class SearchViewModel(
    getRecentSearchUC: GetRecentSearchUseCase,
    private val addSearchUC: AddRecentSearchUseCase,
    private val clearRecentSearch: ClearRecentSearchUseCase,
    private val getAirportsUC: GetAirportsUseCase
) : ViewModel() {

    val getRecentSearch = getRecentSearchUC.getRecentSearch().asLiveData()

    fun getSearchedAirport(keyword: String): LiveData<CommonResult<List<SearchModel>>> =
        getAirportsUC.getSearchedAirport(keyword).asLiveData()


    fun addSearch(search: SearchModel) {
        viewModelScope.launch {
            addSearchUC.addRecentSearch(search)
        }
    }

    fun clearRecentSearch() {
        viewModelScope.launch {
            clearRecentSearch.clear()
        }
    }

}